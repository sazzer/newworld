package uk.co.grahamcox.worlds.service.openid.webapp

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import uk.co.grahamcox.worlds.service.users.UserRetriever

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
 */
@Controller
@RequestMapping("/openid/authorize", method = [RequestMethod.GET])
class AuthorizeController(private val userRetriever: UserRetriever) {
    /**
     * Handle the request for an Implicit Authorization Flow, when the response type is "id_token token"
     */
    @RequestMapping(params = ["response_type=id_token token"])
    fun startImplicitFlowToken(command: AuthorizeCommand): ModelAndView {
        return processStartFlow(command)
    }

    /**
     * Handle the request for an Implicit Authorization Flow, when the response type is "id_token"
     */
    @RequestMapping(params = ["response_type=id_token"])
    fun startImplicitFlowIdToken(command: AuthorizeCommand): ModelAndView {
        return processStartFlow(command)
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
     * Handle the request for starting an Authorization Flow
     */
    private fun processStartFlow(command: AuthorizeCommand): ModelAndView {
        val missingParams = listOfNotNull(
                if (command.clientId.isNullOrBlank()) "client_id" else null,
                if (command.scope.isNullOrBlank()) "scope" else null,
                if (command.redirectUri.isNullOrBlank()) "redirect_uri" else null,
                if (command.nonce.isNullOrBlank()) "nonce" else null
        )

        return if (missingParams.isEmpty()) {
            ModelAndView("/openid/start", mapOf(
                    "parameters" to command
            ))
        } else {
            throw MissingParametersException(missingParams)
        }
    }

    /**
     * Handler to continue the auth flow, checking if the provided email address exists and showing either the Login
     * or Register form as appropriate
     */
    @RequestMapping(value = ["/continue"], method = [RequestMethod.POST])
    fun continueAuthentication(command: AuthorizeCommand, @RequestParam("email") email: String?): ModelAndView {
        return if (email.isNullOrBlank()) {
            processStartFlow(command)
        } else {
            val user = userRetriever.getByEmail(email!!)

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
     * Handler to register a new user, or display an error if registration fails for some reason
     */
    @RequestMapping(value = ["/register"], method = [RequestMethod.POST])
    fun register(command: AuthorizeCommand,
                 @RequestParam("email") email: String,
                 @RequestParam("password") password: String?,
                 @RequestParam("password2") password2: String?,
                 @RequestParam("username") username: String?,
                 @RequestParam("display_name") displayName: String?): ModelAndView {

        // First, check if the email address exists. If so then display the Login form instead, with a message indicating
        // what's happened
        val user = userRetriever.getByEmail(email)

        return if (user != null) {
            ModelAndView("/openid/login", mapOf(
                    "parameters" to command,
                    "email" to email,
                    "email_exists_error" to true
            ))
        } else {
            // Next, perform some validation of our inputs.
            val errors = mapOf(
                    "blank_password" to password.isNullOrBlank(),
                    "password_mismatch" to (password != password2),
                    "blank_username" to username.isNullOrBlank()
            ).filter { it.value }

            if (errors.isNotEmpty()) {
                // If any of this validation fails then display the Register form again, with the error messages
                ModelAndView("/openid/register", mapOf(
                        "parameters" to command,
                        "email" to email,
                        "username" to username,
                        "displayName" to displayName
                ) + errors)
            } else {
                // Otherwise proceed to actually registering the user
                ModelAndView("/openid/register", mapOf(
                        "parameters" to command,
                        "email" to email
                ))
            }

        }
    }

    /**
     * Handle the request for an unsupported response_type value
     */
    @RequestMapping(params = ["response_type!=id_token", "response_type!=id_token token"])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun startUnknownFlow(@RequestParam("response_type") responseType: String?): ModelAndView {
        return ModelAndView("/openid/badResponseType",
                mapOf(
                        if (responseType.isNullOrBlank()) {
                            "missing_parameters" to listOf("response_type")
                        } else {
                            "unsupported_response_type" to responseType
                        }
                ))
    }
}
