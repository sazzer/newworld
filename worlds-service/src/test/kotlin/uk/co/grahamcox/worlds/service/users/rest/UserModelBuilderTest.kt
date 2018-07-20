package uk.co.grahamcox.worlds.service.users.rest

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import uk.co.grahamcox.worlds.service.model.Identity
import uk.co.grahamcox.worlds.service.model.Resource
import uk.co.grahamcox.worlds.service.openid.rest.AccessTokenHolder
import uk.co.grahamcox.worlds.service.openid.token.AccessToken
import uk.co.grahamcox.worlds.service.openid.token.AccessTokenId
import uk.co.grahamcox.worlds.service.rest.hal.Link
import uk.co.grahamcox.worlds.service.rest.hal.LinkBuilder
import uk.co.grahamcox.worlds.service.users.UserData
import uk.co.grahamcox.worlds.service.users.UserId
import uk.co.grahamcox.worlds.service.users.password.Password
import java.net.URI
import java.time.Instant
import java.util.*

/**
 * Unit tests for the User Model Builder
 */
internal class UserModelBuilderTest {
    companion object {
        /** The ID of the user */
        private val USER_ID = UUID.randomUUID().toString()

        /** The self link for the user */
        private val SELF_LINK = Link(href = URI("/api/users/$USER_ID"))

        /** The change password link for the user */
        private val PASSWORD_LINK = Link(href = URI("/api/users/$USER_ID/password"))

        /** The User Model to use */
        private val USER = Resource(
                identity = Identity(
                        id = UserId(USER_ID),
                        version = UUID.randomUUID().toString(),
                        created = Instant.now(),
                        updated = Instant.now()
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
    }

    /** The mock Access Token Holder */
    private val accessTokenHolder: AccessTokenHolder = mockk()

    /** The mock user link builder */
    private val userLinkBuilder: LinkBuilder<String> = mockk()

    /** The mock change password link builder */
    private val changePasswordLinkBuilder: LinkBuilder<String> = mockk()

    /** The test subject */
    private val testSubject = UserModelBuilder(accessTokenHolder, userLinkBuilder, changePasswordLinkBuilder)

    /**
     * Test building the model when there is no current user
     */
    @Test
    fun testBuildNoCurrentUser() {
        every { accessTokenHolder.accessToken } returns null
        every { userLinkBuilder.buildLink(USER_ID) } returns SELF_LINK

        val model = testSubject.build(USER)

        Assertions.assertAll(
                Executable { Assertions.assertEquals(USER_ID, model.id) },
                Executable { Assertions.assertEquals(null, model.email) },
                Executable { Assertions.assertEquals("testuser", model.username) },
                Executable { Assertions.assertEquals("Test User", model.displayName) },
                Executable { Assertions.assertEquals(SELF_LINK, model.links.self) },
                Executable { Assertions.assertEquals(null, model.links.changePassword) }
        )
    }

    /**
     * Test building the model when there is a current user but not for the requested user
     */
    @Test
    fun testBuildWrongCurrentUser() {
        val accessToken = AccessToken(
                user = UserId(UUID.randomUUID().toString()),
                created = Instant.now(),
                expires = Instant.now(),
                scopes = emptySet(),
                id = AccessTokenId(UUID.randomUUID().toString())
        )
        every { accessTokenHolder.accessToken } returns accessToken
        every { userLinkBuilder.buildLink(USER_ID) } returns SELF_LINK

        val model = testSubject.build(USER)

        Assertions.assertAll(
                Executable { Assertions.assertEquals(USER_ID, model.id) },
                Executable { Assertions.assertEquals(null, model.email) },
                Executable { Assertions.assertEquals("testuser", model.username) },
                Executable { Assertions.assertEquals("Test User", model.displayName) },
                Executable { Assertions.assertEquals(SELF_LINK, model.links.self) },
                Executable { Assertions.assertEquals(null, model.links.changePassword) }
        )
    }

    /**
     * Test building the model when there is a current user of the requested user
     */
    @Test
    fun testBuildCorrectCurrentUser() {
        val accessToken = AccessToken(
                user = UserId(USER_ID),
                created = Instant.now(),
                expires = Instant.now(),
                scopes = emptySet(),
                id = AccessTokenId(UUID.randomUUID().toString())
        )
        every { accessTokenHolder.accessToken } returns accessToken
        every { userLinkBuilder.buildLink(USER_ID) } returns SELF_LINK
        every { changePasswordLinkBuilder.buildLink(USER_ID) } returns PASSWORD_LINK

        val model = testSubject.build(USER)

        Assertions.assertAll(
                Executable { Assertions.assertEquals(USER_ID, model.id) },
                Executable { Assertions.assertEquals("test@example.com", model.email) },
                Executable { Assertions.assertEquals("testuser", model.username) },
                Executable { Assertions.assertEquals("Test User", model.displayName) },
                Executable { Assertions.assertEquals(SELF_LINK, model.links.self) },
                Executable { Assertions.assertEquals(PASSWORD_LINK, model.links.changePassword) }
        )
    }
}
