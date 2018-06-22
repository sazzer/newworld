package uk.co.grahamcox.worlds.service.openid.authorization

import uk.co.grahamcox.worlds.service.openid.scopes.Scope
import uk.co.grahamcox.worlds.service.openid.token.AccessToken
import uk.co.grahamcox.worlds.service.users.UserId

/**
 * Mechanism by which a request can be authorized
 */
class Authorizer(private val accessToken: AccessToken) {
    /**
     * Ensure that the access token is for the given User ID
     */
    fun sameUser(user: UserId) {
        if (accessToken.user != user) {
            throw AuthorizationFailedException()
        }
    }

    /**
     * Ensure that the access token is for the given scope
     */
    fun hasScope(scope: Scope) {
        if (!accessToken.scopes.contains(scope)) {
            throw AuthorizationFailedException()
        }
    }
}
