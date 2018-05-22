package uk.co.grahamcox.worlds.service.users

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import uk.co.grahamcox.worlds.service.users.dao.UserEntity
import uk.co.grahamcox.worlds.service.users.dao.UsersRepository
import java.time.Instant
import java.util.*

/**
 * Unit tests for the User Service
 */
internal class UserServiceImplTest {
    companion object {
        /** The User ID to use in the tests */
        private const val USER_ID = "00000000-0000-0000-0000-000000000001"
    }
    /** The mock DAO */
    private val dao = mockk<UsersRepository>()

    /** The test subject */
    private val testSubject = UserServiceImpl(dao)

    /**
     * Test getting a user by ID when the user doesn't exist
     */
    @Test
    fun getUnknownUser() {
        every { dao.findById(USER_ID) } returns Optional.empty()

        Assertions.assertThrows(UserNotFoundException::class.java) {
            testSubject.getById(UserId(USER_ID))
        }

        verify { dao.findById(USER_ID) }
    }

    @Test
    fun getKnownUser() {
        val userEntity = UserEntity(
                id = USER_ID,
                version = "00000000-0000-0000-0000-000000000002",
                created = Instant.parse("2018-05-22T18:53:00Z"),
                updated = Instant.parse("2018-05-22T18:54:00Z"),
                displayName = "Test User",
                email = "test@example.com",
                passwordHash = Base64.getEncoder().encodeToString("password".toByteArray()),
                paswordSalt = Base64.getEncoder().encodeToString("salt".toByteArray())
        )

        every { dao.findById(USER_ID) } returns Optional.of(userEntity)

        val user = testSubject.getById(UserId(USER_ID))

        Assertions.assertAll(
                Executable { Assertions.assertEquals(UserId(USER_ID), user.identity.id) },
                Executable { Assertions.assertEquals("00000000-0000-0000-0000-000000000002", user.identity.version) },
                Executable { Assertions.assertEquals(Instant.parse("2018-05-22T18:53:00Z"), user.identity.created) },
                Executable { Assertions.assertEquals(Instant.parse("2018-05-22T18:54:00Z"), user.identity.updated) },

                Executable { Assertions.assertEquals("Test User", user.data.displayName) },
                Executable { Assertions.assertEquals("test@example.com", user.data.email) },
                Executable { Assertions.assertArrayEquals("password".toByteArray(), user.data.password.hash) },
                Executable { Assertions.assertArrayEquals("salt".toByteArray(), user.data.password.salt) }
        )
        verify { dao.findById(USER_ID) }
    }
}
