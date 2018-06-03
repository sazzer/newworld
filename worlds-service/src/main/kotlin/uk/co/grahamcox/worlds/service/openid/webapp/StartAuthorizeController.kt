package uk.co.grahamcox.worlds.service.openid.webapp

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import uk.co.grahamcox.worlds.service.openid.responseTypes.ResponseTypes
import uk.co.grahamcox.worlds.service.openid.scopes.ScopeRegistry
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
 *
 * Callback Handling
 * -----------------
 * A redirect is made to redirect_url, with parameters of:
 * * code - If the response_Type contains "code"
 *
 * * id_token - If the response_type contains "id_token"
 *
 * * access_token - If the response_type contains "token"
 * * token_type=Bearer - If the response_type contains "token"
 * * expires_in - If the response_type contains "token"
 *
 * * state - The same value as was provided in the "state" parameter
 *
 * If the response contains an access_token then it must be made as a Fragment call. Otherwise, make it as a Query String
 */
@Controller
@RequestMapping("/openid/authorize", method = [RequestMethod.GET])
class StartAuthorizeController(
        private val userService: UserService,
        scopeRegistry: ScopeRegistry,
        supportedResponseTypes: Map<String, Set<ResponseTypes>>
) : AuthorizeControllerBase(scopeRegistry, supportedResponseTypes) {

    /**
     * Start the flow for authorization
     * @param command The command details to use
     */
    @RequestMapping
    fun start(command: AuthorizeCommand): ModelAndView {
        verifyCommand(command)

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

}
