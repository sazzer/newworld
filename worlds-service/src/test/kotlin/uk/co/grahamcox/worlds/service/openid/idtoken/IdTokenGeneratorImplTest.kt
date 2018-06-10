package uk.co.grahamcox.worlds.service.openid.idtoken

import com.auth0.jwt.algorithms.Algorithm
import org.junit.Test
import org.junit.jupiter.api.Assertions
import uk.co.grahamcox.worlds.service.model.Identity
import uk.co.grahamcox.worlds.service.model.Resource
import uk.co.grahamcox.worlds.service.openid.client.ClientId
import uk.co.grahamcox.worlds.service.users.UserData
import uk.co.grahamcox.worlds.service.users.UserId
import uk.co.grahamcox.worlds.service.users.password.Password
import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.util.*

/**
 * Unit tests for the ID Token Generator
 */
internal class IdTokenGeneratorImplTest {
    /** The "current time" for the test */
    private val now = Instant.parse("2018-06-10T12:51:00Z")

    /** The user to generate the ID Token for */
    private val user = Resource(
            identity = Identity(
                    id = UserId("userId"),
                    version = UUID.randomUUID().toString(),
                    created = now,
                    updated = now
            ),
            data = UserData(
                    email = "test@example.com",
                    username = "testuser",
                    displayName = "Test User",
                    password = Password(
                            hash = "hash".toByteArray(),
                            salt = "salt".toByteArray()
                    )
            )
    )

    /** The Client ID */
    private val clientId = ClientId("clientId")

    /** The test subject */
    private val testSubject = IdTokenGeneratorImpl(
            clock = Clock.fixed(now, ZoneId.of("UTC")),
            duration = Duration.ofDays(5),
            signingAlgorithm = Algorithm.HMAC512("SomeSecret")
    )

    /**
     * Test when a nonce was provided
     */
    @Test
    fun testWithNonce() {
        val token = testSubject.generate(user, clientId, "myNonce")

        Assertions.assertEquals("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VySWQiLCJhdWQiOiJjbGllbnRJZCIsImlzcyI6InVrLmNvLmdyYWhhbWNveC53b3JsZHMuc2VydmljZS5vcGVuaWQuaWR0b2tlbi5JZFRva2VuR2VuZXJhdG9ySW1wbCIsImV4cCI6MTUyOTA2NzA2MCwiaWF0IjoxNTI4NjM1MDYwLCJub25jZSI6Im15Tm9uY2UifQ.Sv8Mr8m_nfmsh0AVE2DEeqpLytI8p8sryaH2R99-9SqA8wckjYrdCS6LGrDJIfO2kpD5fsullmaruSsLECjTGA",
                token)
    }

    /**
     * Test when a nonce was not provided
     */
    @Test
    fun testWithoutNonce() {
        val token = testSubject.generate(user, clientId, null)

        Assertions.assertEquals("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VySWQiLCJhdWQiOiJjbGllbnRJZCIsImlzcyI6InVrLmNvLmdyYWhhbWNveC53b3JsZHMuc2VydmljZS5vcGVuaWQuaWR0b2tlbi5JZFRva2VuR2VuZXJhdG9ySW1wbCIsImV4cCI6MTUyOTA2NzA2MCwiaWF0IjoxNTI4NjM1MDYwfQ.NumQBNvGT-TVAJ_-mTbo4XFJ7D49sU0REVF5LXWr5s03LUAsjpisyaEpuhS6W3GB1VmCVu0-wrXjQmpCUFgXXA",
                token)
    }
}
