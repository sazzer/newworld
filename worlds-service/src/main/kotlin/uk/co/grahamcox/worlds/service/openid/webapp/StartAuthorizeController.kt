package uk.co.grahamcox.worlds.service.openid.webapp

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import uk.co.grahamcox.worlds.service.openid.responseTypes.ResponseTypes
import uk.co.grahamcox.worlds.service.users.UserService

/**
 * Controller for managing the OpenID Connect flows for the /authorize endpoint.
 *
 * Authorization Code Flow
 * -----------------------
 * responseType: code
 * Required Parameters: scope, responseType, clientId, redirectUri
 * Recommended Parameters: state
 * Optional Parameters: response_mode, nonce, display, prompt, max_age, ui_locales, id_token_hint, login_hint, acr_values
 *
 * Implicit Flow
 * -------------
 * responseType: id_token token, id_token
 * Required Parameters: scope, responseType, clientId, redirectUri, nonce
 * Recommended Parameters: state
 * Optional Parameters: response_mode, display, prompt, max_age, ui_locales, id_token_hint, login_hint, acr_values
 *
 * Hybrid Flow
 * -----------
 * responseType: code id_token, code token, code id_token token
 * Required Parameters: scope, responseType, clientId, redirectUri
 * Recommended Parameters: state
 * Optional Parameters: response_mode, nonce, display, prompt, max_age, ui_locales, id_token_hint, login_hint, acr_values
 *
 * Difference between State and Nonce
 * ----------------------------------
 * If a State is provided, it is returned to the client as a parameter on the redirectUri call
 * If a Nonce is provided, it is returned to the client as a field in the returned ID Token
 *
 * The ID Token *might* be returned by a separate API call to a separate endpoint - the /token endpoint - and the Nonce
 * validates that the ID Token retrieved is indeed for the /authorize call that was made.
 *
 * The State simply guarantees that the call to the redirectUri has come from the call to the /authorize endpoint, and
 * is not a malicious third party calling the redirectUri manually.
 *
 * Response Types
 * --------------
 * * code => Return an Authorization Code that can be exchanged for an Access Token
 * * id_token => Return an ID Token describing the user
 * * token => Return an Access Token directly
 *
 * This is the only real difference between the different flows.
 */
@Controller
@RequestMapping("/openid/authorize", method = [RequestMethod.GET])
class StartAuthorizeController(
        private val userService: UserService,
        private val supportedResponseTypes: Map<String, Set<ResponseTypes>>
) {

    /**
     * Start the flow for authorization
     * @param command The command details to use
     */
    @RequestMapping
    fun start(command: AuthorizeCommand): ModelAndView {
        if (command.responseType == null) {
            throw MissingParametersException(listOf("response_type"))
        }

        val responseTypes = supportedResponseTypes[command.responseType]
            ?: throw UnsupportedResponseTypeException(command.responseType)

        val missingParams = listOfNotNull(
                if (command.clientId.isNullOrBlank()) "client_id" else null,
                if (command.scope.isNullOrBlank()) "scope" else null,
                if (command.redirectUri.isNullOrBlank()) "redirect_uri" else null,
                if (!responseTypes.contains(ResponseTypes.CODE) && command.nonce.isNullOrBlank()) "nonce" else null
        )

        if (missingParams.isNotEmpty()) {
            throw MissingParametersException(missingParams)
        }

        return ModelAndView("/openid/start", mapOf("parameters" to command))
    }

    /**
     * Handler to continue the auth flow, checking if the provided email address exists and showing either the Login
     * or Register form as appropriate
     */
    @RequestMapping(value = ["/continue"], method = [RequestMethod.POST])
    fun continueAuthentication(command: AuthorizeCommand, @RequestParam("email") email: String?): ModelAndView {
        return if (email.isNullOrBlank()) {
            start(command)
        } else {
            val user = userService.getByEmail(email!!)

            val view = if (user != null) {
                "/openid/login"
            } else {
                "/openid/register"
            }

            ModelAndView(view, mapOf(
                    "parameters" to command,
                    "email" to email
            ))
        }
    }

    /**
     * Handle when there were mandatory parameters that are missing
     */
    @ExceptionHandler(MissingParametersException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMissingParameters(e: MissingParametersException) =
            ModelAndView("/openid/badResponseType", mapOf(
                    "missing_parameters" to e.missingParameters
            ))

    /**
     * Handle when there was a response_type string that isn't supported
     */
    @ExceptionHandler(UnsupportedResponseTypeException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleUnsupportedResponseType(e: UnsupportedResponseTypeException) =
            ModelAndView("/openid/badResponseType", mapOf(
                    "unsupported_response_type" to e.responseType
            ))
}
