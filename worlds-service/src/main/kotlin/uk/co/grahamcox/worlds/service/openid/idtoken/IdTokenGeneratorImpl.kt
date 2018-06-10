package uk.co.grahamcox.worlds.service.openid.idtoken

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.slf4j.LoggerFactory
import uk.co.grahamcox.worlds.service.model.Resource
import uk.co.grahamcox.worlds.service.openid.client.ClientId
import uk.co.grahamcox.worlds.service.users.UserData
import uk.co.grahamcox.worlds.service.users.UserId
import java.time.Clock
import java.time.temporal.TemporalAmount
import java.util.*

/**
 * Standard implementation of the ID Token Generator
 */
class IdTokenGeneratorImpl(
        private val clock: Clock,
        private val duration: TemporalAmount,
        private val signingAlgorithm: Algorithm
) : IdTokenGenerator {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(IdTokenGeneratorImpl::class.java)
    }

    /**
     * Generate the ID Token for the given User, Client and with the provided Nonce
     * @param user The user that the ID Token represents
     * @param client The ID of the Client that the ID Token is for
     * @param nonce The nonce of the ID Token
     */
    override fun generate(user: Resource<UserId, UserData>, client: ClientId, nonce: String?): String {
        val now = clock.instant()

        val token = JWT.create()
                .withIssuer(IdTokenGeneratorImpl::class.qualifiedName)
                .withSubject(user.identity.id.id)
                .withAudience(client.id)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plus(duration)))
                .withClaim("nonce", nonce)
                .sign(signingAlgorithm)

        LOG.debug("Generated ID Token {} for User {} and Client {}",
                token, user, client)

        return token
    }
}
