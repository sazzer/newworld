package uk.co.grahamcox.worlds.service.openid.token

import uk.co.grahamcox.worlds.service.openid.scopes.Scope
import uk.co.grahamcox.worlds.service.users.UserId
import java.time.Clock
import java.time.temporal.TemporalAmount
import java.util.*

/**
 * Standard implementation of the Access Token Generator
 */
class AccessTokenGeneratorImpl(
        private val clock: Clock,
        private val duration: TemporalAmount) : AccessTokenGenerator {
    /**
     * Generate the Access Token for this user that is authorised for these scopes
     * @param user The user ID
     * @param scopes the scopes the access token is authorised for
     * @return the access token
     */
    override fun generate(user: UserId, scopes: Set<Scope>): AccessToken {
        val now = clock.instant()
        return AccessToken(
                id = AccessTokenId(UUID.randomUUID().toString()),
                user = user,
                created = now,
                expires = now.plus(duration),
                scopes = scopes
        )
    }
}
