package uk.co.grahamcox.worlds.service.openid.token

import uk.co.grahamcox.worlds.service.openid.scopes.Scope
import uk.co.grahamcox.worlds.service.users.UserId
import java.time.Instant

/**
 * Representation of an Access Token that can be used for authorization purposes
 */
data class AccessToken(
        val id: AccessTokenId,
        val user: UserId,
        val created: Instant,
        val expires: Instant,
        val scopes: Set<Scope>
)
