package uk.co.grahamcox.worlds.service.openid.rest

import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import uk.co.grahamcox.worlds.service.openid.token.AccessToken
import uk.co.grahamcox.worlds.service.openid.token.AccessTokenId
import uk.co.grahamcox.worlds.service.openid.token.AccessTokenSerializer
import uk.co.grahamcox.worlds.service.openid.token.InvalidJwtException
import uk.co.grahamcox.worlds.service.users.UserId
import java.time.Instant
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Unit tests for the Access Token Interceptor
 */
internal class AccessTokenInterceptorTest {
    /** The access token store */
    private val accessTokenStore: AccessTokenStore = mockk()

    /** The access token serializer */
    private val accessTokenSerializer: AccessTokenSerializer = mockk()

    /** The incoming request */
    private val request: HttpServletRequest = mockk()

    /** The outgoing response */
    private val response: HttpServletResponse = mockk()

    /** The handler */
    private val handler = "Handler"

    /** the test subject */
    private val testSubject = AccessTokenInterceptor(accessTokenStore, accessTokenSerializer)

    /**
     * Test that we reset the store at the end of the request
     */
    @Test
    fun testEndRequest() {
        every { accessTokenStore.reset() } just Runs

        testSubject.afterCompletion(request, response, handler, null)

        verify { accessTokenStore.reset() }
    }

    /**
     * Test that when there was no Authorization header then nothing happens
     */
    @Test
    fun testNoAuthHeader() {
        every { request.getHeader("Authorization") } returns null

        testSubject.preHandle(request, response, handler)
    }

    /**
     * Test that when there was an Authorization header but it wasn't a Bearer token then nothing happens
     */
    @Test
    fun testNoBearerAuthHeader() {
        every { request.getHeader("Authorization") } returns "Basic abc123"

        testSubject.preHandle(request, response, handler)
    }

    /**
     * Test that when there was an Authorization header but it wasn't a valid Bearer token then an error occurs
     */
    @Test
    fun testNoValidBearerAuthHeader() {
        every { request.getHeader("Authorization") } returns "Bearer abc123"
        every { accessTokenSerializer.deserialize("abc123") } throws InvalidJwtException()

        Assertions.assertThrows(InvalidJwtException::class.java) {
            testSubject.preHandle(request, response, handler)
        }
    }

    /**
     * Test that when there was an Authorization header and it was a valid Bearer token then the access token is stored
     */
    @Test
    fun testValidBearerAuthHeader() {
        val accessToken = AccessToken(
                id = AccessTokenId("accessToken"),
                user = UserId("user"),
                created = Instant.EPOCH,
                expires = Instant.MAX,
                scopes = setOf()
        )

        every { request.getHeader("Authorization") } returns "Bearer abc123"
        every { accessTokenSerializer.deserialize("abc123") } returns accessToken
        every { accessTokenStore.accessToken = accessToken } just Runs

        testSubject.preHandle(request, response, handler)

        verify { accessTokenStore.accessToken = accessToken }
    }
}
