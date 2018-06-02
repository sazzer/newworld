package uk.co.grahamcox.worlds.service.openid.token

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import uk.co.grahamcox.worlds.service.openid.OpenIdScopes
import uk.co.grahamcox.worlds.service.users.UserId
import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.ZoneId

/**
 * Unit tests for the Access Token Generator
 */
internal class AccessTokenGeneratorImplTest {
    companion object {
        /** The current time */
        private val NOW = Instant.parse("2018-06-02T12:16:00Z")
    }

    /** The test subject */
    val testSubject = AccessTokenGeneratorImpl(Clock.fixed(NOW, ZoneId.of("UTC")), Duration.ofDays(5))

    /**
     * Test generating an access token
     */
    @Test
    fun generateAccessToken() {
        val accessToken = testSubject.generate(UserId("user"), setOf(OpenIdScopes.OPENID))

        Assertions.assertAll(
                Executable { Assertions.assertEquals(UserId("user"), accessToken.user) },
                Executable { Assertions.assertEquals(NOW, accessToken.created) },
                Executable { Assertions.assertEquals(NOW.plus(Duration.ofDays(5)), accessToken.expires) },
                Executable { Assertions.assertEquals(setOf(OpenIdScopes.OPENID), accessToken.scopes) }
        )
    }
}
