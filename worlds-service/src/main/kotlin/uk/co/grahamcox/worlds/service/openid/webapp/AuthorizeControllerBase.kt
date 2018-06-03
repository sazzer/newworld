package uk.co.grahamcox.worlds.service.openid.webapp

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.ModelAndView
import uk.co.grahamcox.worlds.service.openid.responseTypes.ResponseTypes
import uk.co.grahamcox.worlds.service.openid.scopes.ScopeRegistry
import uk.co.grahamcox.worlds.service.openid.scopes.UnknownScopesException

/**
 * Base class for the Authorize Controller classes to build upon
 */
open class AuthorizeControllerBase(
        private val scopeRegistry: ScopeRegistry,
        private val supportedResponseTypes: Map<String, Set<ResponseTypes>>
) {

    /**
     * Verify that the Authorize Command has valid values in it
     * @param command The command object to verify
     * @return the set of response types that are supported
     */
    protected fun verifyCommand(command: AuthorizeCommand): Set<ResponseTypes> {
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

        scopeRegistry.parseScopeString(command.scope!!)
        return responseTypes
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

    /**
     * Handle when there was a scopes string that contains unknown scopes
     */
    @ExceptionHandler(UnknownScopesException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleUnknownScopes(e: UnknownScopesException) =
            ModelAndView("/openid/badResponseType", mapOf(
                    "unsupported_scopes" to e.unknownScopes
            ))
}
