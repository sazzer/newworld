package uk.co.grahamcox.worlds.service.openid.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import org.slf4j.LoggerFactory
import uk.co.grahamcox.worlds.service.openid.scopes.ScopeRegistry
import uk.co.grahamcox.worlds.service.users.UserId
import java.util.*

/**
 * Implementation of the Access Token Serializer in terms of JWTs
 */
class JwtAccessTokenSerializerImpl(
        private val signingAlgorithm: Algorithm,
        private val scopesRegistry: ScopeRegistry
) : AccessTokenSerializer {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(JwtAccessTokenSerializerImpl::class.java)
    }
    /**
     * Serialize an access token into a string
     * @param accessToken The access token to serialize
     * @return the serialized access token
     */
    override fun serialize(accessToken: AccessToken): String {
        val jwt = JWT.create()
                .withJWTId(accessToken.id.id)
                .withIssuer(JwtAccessTokenSerializerImpl::class.qualifiedName)
                .withAudience(JwtAccessTokenSerializerImpl::class.qualifiedName)
                .withSubject(accessToken.user.id)
                .withIssuedAt(Date.from(accessToken.created))
                .withExpiresAt(Date.from(accessToken.expires))
                .withArrayClaim("scopes", accessToken.scopes.map { it.id }.toTypedArray())
                .sign(signingAlgorithm)

        LOG.debug("Generated JWT {} for Access Token {}", jwt, accessToken)
        return jwt
    }

    /**
     * Deserialize an access token from a string
     * @param accessToken The access token to deserialize
     * @return the deserialized access token
     */
    override fun deserialize(accessToken: String): AccessToken {
        val decoded = try {
            JWT.require(signingAlgorithm)
                    .withIssuer(JwtAccessTokenSerializerImpl::class.qualifiedName)
                    .withAudience(JwtAccessTokenSerializerImpl::class.qualifiedName)
                    .build()
                    .verify(accessToken)
        } catch (e: TokenExpiredException) {
            LOG.info("Access token has already expired", e)
            throw ExpiredJwtException()
        } catch (e: JWTVerificationException) {
            LOG.warn("Invalid access token provided", e)
            throw InvalidJwtException()
        }

        val result = AccessToken(
                id = AccessTokenId(decoded.id),
                user = UserId(decoded.subject),
                created = decoded.issuedAt.toInstant(),
                expires = decoded.expiresAt.toInstant(),
                scopes = decoded.getClaim("scopes")
                        .asArray(String::class.java)
                        .mapNotNull { scopesRegistry.scopesMap[it] }
                        .toSet()
        )

        LOG.debug("Generated Access Token {} from JWT {}", result, accessToken)
        return result
    }
}
