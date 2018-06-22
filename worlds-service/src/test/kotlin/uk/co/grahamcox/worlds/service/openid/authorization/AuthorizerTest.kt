package uk.co.grahamcox.worlds.service.openid.authorization

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import uk.co.grahamcox.worlds.service.openid.OpenIdScopes
import uk.co.grahamcox.worlds.service.openid.token.AccessToken
import uk.co.grahamcox.worlds.service.openid.token.AccessTokenId
import uk.co.grahamcox.worlds.service.users.UserId
import java.time.Instant

/**
 * Unit tests for the [Authorizer]
 */
internal class AuthorizerTest {
    /** The access token */
    private val accessToken = AccessToken(
            id = AccessTokenId("accessTokenId"),
            user = UserId("userId"),
            created = Instant.parse("2018-06-03T12:09:00Z"),
            expires = Instant.parse("2019-06-03T12:09:00Z"),
            scopes = setOf(OpenIdScopes.OPENID)
    )

    /** The test subject */
    private val testSubject = Authorizer(accessToken)

    /**
     * Test when we want to be a given user ID and we are
     */
    @Test
    fun testSameUser() {
        testSubject.sameUser(UserId("userId"))
    }

    /**
     * Test when we want to be a given user ID and we aren't
     */
    @Test
    fun testDifferentUser() {
        Assertions.assertThrows(AuthorizationFailedException::class.java) {
            testSubject.sameUser(UserId("otherId"))
        }
    }

    /**
     * Test when we want to have a given scope and we do
     */
    @Test
    fun testHasScope() {
        testSubject.hasScope(OpenIdScopes.OPENID)
    }

    /**
     * Test when we want to have a given scope and we don't
     */
    @Test
    fun testDoesntHaveScope() {
        Assertions.assertThrows(AuthorizationFailedException::class.java) {
            testSubject.hasScope(OpenIdScopes.EMAIL)
        }
    }
}
