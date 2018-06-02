package uk.co.grahamcox.worlds.service.openid.token

import uk.co.grahamcox.worlds.service.openid.scopes.Scope
import uk.co.grahamcox.worlds.service.users.UserId

/**
 * Mechanism to generate an Access Token for a user
 */
interface AccessTokenGenerator {
    /**
     * Generate the Access Token for this user that is authorised for these scopes
     * @param user The user ID
     * @param scopes the scopes the access token is authorised for
     * @return the access token
     */
    fun generate(user: UserId, scopes: Set<Scope>): AccessToken
}
