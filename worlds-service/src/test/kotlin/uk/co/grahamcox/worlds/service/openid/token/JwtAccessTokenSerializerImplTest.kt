package uk.co.grahamcox.worlds.service.openid.token

import com.auth0.jwt.algorithms.Algorithm
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import uk.co.grahamcox.worlds.service.openid.OpenIdScopes
import uk.co.grahamcox.worlds.service.openid.scopes.ScopeRegistry
import uk.co.grahamcox.worlds.service.users.UserId
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * Unit Tests for serializing an Access Token to a JWT
 */
internal class JwtAccessTokenSerializerImplTest {
    /** The test subject */
    private val testSubject = JwtAccessTokenSerializerImpl(
            Algorithm.HMAC512("superSecret"),
            ScopeRegistry(setOf(OpenIdScopes.OPENID)))

    /**
     * Test serializing an Access Token into a String
     */
    @Test
    fun testSerialize() {
        val accessToken = AccessToken(
                id = AccessTokenId("accessTokenId"),
                user = UserId("userId"),
                created = Instant.parse("2018-06-03T12:09:00Z"),
                expires = Instant.parse("2019-06-03T12:09:00Z"),
                scopes = setOf(OpenIdScopes.OPENID)
        )
        val jwt = testSubject.serialize(accessToken)

        Assertions.assertEquals("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJ1ay5jby5ncmFoYW1jb3gud29ybGRzLnNlcnZpY2Uub3BlbmlkLnRva2VuLkp3dEFjY2Vzc1Rva2VuU2VyaWFsaXplckltcGwiLCJzdWIiOiJ1c2VySWQiLCJpc3MiOiJ1ay5jby5ncmFoYW1jb3gud29ybGRzLnNlcnZpY2Uub3BlbmlkLnRva2VuLkp3dEFjY2Vzc1Rva2VuU2VyaWFsaXplckltcGwiLCJzY29wZXMiOlsib3BlbmlkIl0sImV4cCI6MTU1OTU2Mzc0MCwiaWF0IjoxNTI4MDI3NzQwLCJqdGkiOiJhY2Nlc3NUb2tlbklkIn0.3wqXgX9lfIYv4MT3BpIB78EIY4vX9zJb1vnTWACRF1lHGopy-y71ghRIr_LhMOB-HXEmg4BtY82_RPiRspE2dA",
                jwt)
    }

    /**
     * Test serializing an Access Token into a String and then back again
     * Unfortunately the JWT Library automatically validates the Expiry Time against the system clock, so we can't use
     * a pre-defined string here
     */
    @Test
    fun testSerializeAndDeserialize() {
        val now = Instant.now().truncatedTo(ChronoUnit.SECONDS)
        val accessToken = AccessToken(
                id = AccessTokenId("accessTokenId"),
                user = UserId("userId"),
                created = now,
                expires = now.plus(Duration.ofDays(1)),
                scopes = setOf(OpenIdScopes.OPENID)
        )

        val jwt = testSubject.serialize(accessToken)
        val result = testSubject.deserialize(jwt)

        Assertions.assertEquals(accessToken, result)
    }

    /**
     * Test deserializing an access token string with unknown scopes mentioned
     */
    @Test
    fun testDeserializeUnknownScopes() {
        val now = Instant.now().truncatedTo(ChronoUnit.SECONDS)
        val accessToken = AccessToken(
                id = AccessTokenId("accessTokenId"),
                user = UserId("userId"),
                created = now,
                expires = now.plus(Duration.ofDays(1)),
                scopes = setOf(OpenIdScopes.OPENID, OpenIdScopes.PROFILE, OpenIdScopes.EMAIL)
        )

        val expected = AccessToken(
                id = AccessTokenId("accessTokenId"),
                user = UserId("userId"),
                created = now,
                expires = now.plus(Duration.ofDays(1)),
                scopes = setOf(OpenIdScopes.OPENID)
        )

        val jwt = testSubject.serialize(accessToken)
        val result = testSubject.deserialize(jwt)

        Assertions.assertEquals(expected, result)
    }

    /**
     * Test deserializing an access token that has expired
     */
    @Test
    fun testDeserializeExpired() {
        val now = Instant.now().truncatedTo(ChronoUnit.SECONDS)
        val accessToken = AccessToken(
                id = AccessTokenId("accessTokenId"),
                user = UserId("userId"),
                created = now,
                expires = now.minus(Duration.ofDays(1)),
                scopes = setOf(OpenIdScopes.OPENID)
        )

        val jwt = testSubject.serialize(accessToken)
        Assertions.assertThrows(ExpiredJwtException::class.java) {
            testSubject.deserialize(jwt)
        }
    }

    /**
     * Test deserializing an access token that is just not valid
     */
    @Test
    fun testDeserializeInvalid() {
        Assertions.assertThrows(InvalidJwtException::class.java) {
            testSubject.deserialize("invalid")
        }
    }

    /**
     * Test deserializing an access token that was signed with a different key
     */
    @Test
    fun testDeserializeBadSignature() {
        val now = Instant.now().truncatedTo(ChronoUnit.SECONDS)
        val accessToken = AccessToken(
                id = AccessTokenId("accessTokenId"),
                user = UserId("userId"),
                created = now,
                expires = now.plus(Duration.ofDays(1)),
                scopes = setOf(OpenIdScopes.OPENID)
        )

        val jwt = JwtAccessTokenSerializerImpl(Algorithm.HMAC512("otherKey"), ScopeRegistry(setOf()))
                .serialize(accessToken)

        Assertions.assertThrows(InvalidJwtException::class.java) {
            testSubject.deserialize(jwt)
        }
    }
}
