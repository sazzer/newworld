package uk.co.grahamcox.worlds.service.users

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import uk.co.grahamcox.worlds.service.model.Identity
import uk.co.grahamcox.worlds.service.model.Resource
import uk.co.grahamcox.worlds.service.users.password.Password
import uk.co.grahamcox.worlds.service.users.password.PasswordHasher
import java.time.Instant

/**
 * Unit tests for the Password Changer
 */
internal class PasswordChangerImplTest {
    companion object {
        /** A User ID to use */
        private val USER_ID = UserId("userId")
    }

    /** The mock user service */
    private val userService = mockk<UserService>()

    /** The mock password hasher */
    private val passwordHasher = mockk<PasswordHasher>()

    /** The test subject */
    private val testSubject = PasswordChangerImpl(userService, passwordHasher)

    /**
     * Test when the user service can't find the user
     */
    @Test
    fun testUnknownUser() {
        every { userService.getById(USER_ID) } throws UserNotFoundException()

        Assertions.assertThrows(UserNotFoundException::class.java) {
            testSubject.changePassword(USER_ID, "old", "new")
        }
    }

    /**
     * Test when the provided old password doesn't match
     */
    @Test
    fun testWrongOldPassword() {
        val salt = "salt".toByteArray(Charsets.UTF_8)
        val currentPassword = Password(
                hash = "hash".toByteArray(Charsets.UTF_8),
                salt = salt
        )

        val user = Resource(
                identity = Identity(
                        id = USER_ID,
                        version = "version",
                        created = Instant.now(),
                        updated = Instant.now()
                ),
                data = UserData(
                        email = "test@example.com",
                        username = "username",
                        displayName = "displayName",
                        password = currentPassword
                )
        )
        every { userService.getById(USER_ID) } returns user
        every { passwordHasher.hashPassword("old", salt) } returns Password(
                "wrong".toByteArray(Charsets.UTF_8),
                salt)

        Assertions.assertThrows(InvalidPasswordException::class.java) {
            testSubject.changePassword(USER_ID, "old", "new")
        }
    }


    /**
     * Test when the provided old password does match
     */
    @Test
    fun testCorrectOldPassword() {
        val salt = "salt".toByteArray(Charsets.UTF_8)
        val currentPassword = Password(
                hash = "hash".toByteArray(Charsets.UTF_8),
                salt = salt
        )
        val newPassword = Password(
                hash = "newhash".toByteArray(Charsets.UTF_8),
                salt = "newsalt".toByteArray(Charsets.UTF_8)
        )

        val user = Resource(
                identity = Identity(
                        id = USER_ID,
                        version = "version",
                        created = Instant.now(),
                        updated = Instant.now()
                ),
                data = UserData(
                        email = "test@example.com",
                        username = "username",
                        displayName = "displayName",
                        password = currentPassword
                )
        )
        every { userService.getById(USER_ID) } returns user
        every { passwordHasher.hashPassword("old", salt) } returns currentPassword
        every { passwordHasher.hashPassword("new") } returns newPassword
        every { userService.update(USER_ID, UserData(
                email = "test@example.com",
                username = "username",
                displayName = "displayName",
                password = newPassword
        )) } returns Resource(
                identity = Identity(
                        id = USER_ID,
                        version = "version",
                        created = Instant.now(),
                        updated = Instant.now()
                ),
                data = UserData(
                        email = "test@example.com",
                        username = "username",
                        displayName = "displayName",
                        password = newPassword
                )
        )

        testSubject.changePassword(USER_ID, "old", "new")
    }
}
