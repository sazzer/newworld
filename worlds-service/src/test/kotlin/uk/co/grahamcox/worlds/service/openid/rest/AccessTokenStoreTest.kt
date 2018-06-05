package uk.co.grahamcox.worlds.service.openid.rest

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import uk.co.grahamcox.worlds.service.CurrentRequest
import uk.co.grahamcox.worlds.service.openid.token.AccessToken
import uk.co.grahamcox.worlds.service.openid.token.AccessTokenId
import uk.co.grahamcox.worlds.service.users.UserId
import java.time.Instant

/**
 * Unit tests for the Access Token Store
 */
@CurrentRequest
internal class AccessTokenStoreTest {
    /** The test subject */
    private val testSubject = AccessTokenStore()

    /**
     * Test getting the access token when it was never set
     */
    @Test
    fun testGetWhenNotSet() {
        Assertions.assertNull(testSubject.accessToken)
    }

    /**
     * Test setting the access token and then getting it back again
     */
    @Test
    fun testGetWhenSet() {
        val accessToken = AccessToken(
                id = AccessTokenId("accessToken"),
                user = UserId("user"),
                created = Instant.EPOCH,
                expires = Instant.MAX,
                scopes = setOf()
        )

        testSubject.accessToken = accessToken
        Assertions.assertSame(accessToken, testSubject.accessToken)
    }

    /**
     * Test getting the access token after it's been reset
     */
    @Test
    fun testGetWhenReset() {
        val accessToken = AccessToken(
                id = AccessTokenId("accessToken"),
                user = UserId("user"),
                created = Instant.EPOCH,
                expires = Instant.MAX,
                scopes = setOf()
        )

        testSubject.accessToken = accessToken
        testSubject.reset()
        Assertions.assertNull(testSubject.accessToken)
    }
}
