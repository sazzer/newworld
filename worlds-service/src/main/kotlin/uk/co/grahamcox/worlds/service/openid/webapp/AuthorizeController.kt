package uk.co.grahamcox.worlds.service.openid.webapp

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
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
        return processImplicitFlow(command)
    }

    /**
     * Handle the request for an Implicit Authorization Flow, when the response type is "id_token"
     */
    @RequestMapping(params = ["response_type=id_token"])
    fun startImplicitFlowIdToken(command: AuthorizeCommand): ModelAndView {
        return processImplicitFlow(command)
    }

    /**
     * Handle the request for an Implicit Authorization Flow
     */
    private fun processImplicitFlow(command: AuthorizeCommand): ModelAndView {
        val missingParams = listOfNotNull(
                if (command.clientId.isNullOrBlank()) "clientId" else null,
                if (command.scope.isNullOrBlank()) "scope" else null,
                if (command.redirectUri.isNullOrBlank()) "redirectUri" else null,
                if (command.nonce.isNullOrBlank()) "nonce" else null
        )

        return if (missingParams.isEmpty()) {
            ModelAndView("/openid/start", mapOf(
                    "parameters" to command
            ))
        } else {
            ModelAndView("/openid/badResponseType", mapOf(
                    "missing_parameters" to missingParams
            ))
        }
    }

    /**
     * Handler to continue the auth flow, checking if the provided email address exists and showing either the Login
     * or Register form as appropriate
     */
    @RequestMapping(value = ["/continue"], method = [RequestMethod.POST])
    fun continueAuthentication(command: AuthorizeCommand, @RequestParam("email") email: String): ModelAndView {
        val user = userRetriever.getByEmail(email)

        val view = if (user != null) {
            "/openid/login"
        } else {
            "/openid/register"
        }

        return ModelAndView(view, mapOf(
                "parameters" to command,
                "email" to email
        ))
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
