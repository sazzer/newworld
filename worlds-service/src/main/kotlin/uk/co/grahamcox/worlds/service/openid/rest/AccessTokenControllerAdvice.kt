package uk.co.grahamcox.worlds.service.openid.rest

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import uk.co.grahamcox.worlds.service.openid.authorization.AuthorizationFailedException

/**
 * Controller Advice to handle situations where authentication has failed
 */
@RestControllerAdvice
class AccessTokenControllerAdvice {
    /**
     * Handle when an access token was required but not present
     */
    @ExceptionHandler(MissingAccessTokenException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleMissingAccessToken() {

    }

    /**
     * Handle when authorization failed for a request
     */
    @ExceptionHandler(AuthorizationFailedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleAuthorizationFailed() {

    }
}
