package uk.co.grahamcox.worlds.service.openid.webapp

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Controller for managing the OpenID Connect flows for the /authorize endpoint.
 *
 * Authorization Code Flow
 * -----------------------
 * response_type: code
 * Required Parameters: scope, response_type, client_id, redirect_uri
 * Recommended Parameters: state
 * Optional Parameters: response_mode, nonce, display, prompt, max_age, ui_locales, id_token_hint, login_hint, acr_values
 *
 * Implicit Flow
 * -------------
 * response_type: id_token token, id_token
 * Required Parameters: scope, response_type, client_id, redirect_uri, nonce
 * Recommended Parameters: state
 * Optional Parameters: response_mode, display, prompt, max_age, ui_locales, id_token_hint, login_hint, acr_values
 *
 * Hybrid Flow
 * -----------
 * response_type: code id_token, code token, code id_token token
 * Required Parameters: scope, response_type, client_id, redirect_uri
 * Recommended Parameters: state
 * Optional Parameters: response_mode, nonce, display, prompt, max_age, ui_locales, id_token_hint, login_hint, acr_values
 *
 * Difference between State and Nonce
 * ----------------------------------
 * If a State is provided, it is returned to the client as a parameter on the redirect_uri call
 * If a Nonce is provided, it is returned to the client as a field in the returned ID Token
 *
 * The ID Token *might* be returned by a separate API call to a separate endpoint - the /token endpoint - and the Nonce
 * validates that the ID Token retrieved is indeed for the /authorize call that was made.
 *
 * The State simply guarantees that the call to the redirect_uri has come from the call to the /authorize endpoint, and
 * is not a malicious third party calling the redirect_uri manually.
 */
@Controller
@RequestMapping("/openid/authorize", method = [RequestMethod.GET])
class AuthorizeController {
    @RequestMapping(params = ["response_type=code"])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun startAuthorizationCodeFlow(@RequestParam("response_type") responseType: String): String {
        return "/openid/badResponseType"
    }

    @RequestMapping(params = ["response_type=id_token token"])
    fun startImplicitFlowToken(command: AuthorizeCommand): String {
        return "/openid/start"
    }

    @RequestMapping(params = ["response_type=id_token"])
    fun startImplicitFlowIdToken(command: AuthorizeCommand): String {
        return "/openid/start"
    }

    @RequestMapping(params = ["response_type=code token"])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun startHybridFlowToken(@RequestParam("response_type") responseType: String): String {
        return "/openid/badResponseType"
    }

    @RequestMapping(params = ["response_type=code id_token"])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun startHybridFlowIdToken(@RequestParam("response_type") responseType: String): String {
        return "/openid/badResponseType"
    }

    @RequestMapping(params = ["response_type=code id_token token"])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun startHybridFlowBoth(@RequestParam("response_type") responseType: String): String {
        return "/openid/badResponseType"
    }

    @RequestMapping(params = ["response_type!=code",
        "response_type!=id_token",
        "response_type!=id_token token",
        "response_type!=code id_token",
        "response_type!=code token",
        "response_type!=code id_token token"])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun startUnknownFlow(@RequestParam("response_type") responseType: String): String {
        return "/openid/badResponseType"
    }

    @RequestMapping(params = ["!response_type"])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun startNoFlow(): String {
        return "/openid/badResponseType"
    }
}
