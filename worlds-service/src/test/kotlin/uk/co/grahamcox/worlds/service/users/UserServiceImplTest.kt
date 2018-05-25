package uk.co.grahamcox.worlds.service.users

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import uk.co.grahamcox.worlds.service.model.Resource
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

        /** The email address to use in the tests */
        private const val EMAIL_ADDRESS = "test@example.com"
    }
    /** The mock DAO */
    private val dao = mockk<UsersRepository>()

    /** The test subject */
    private val testSubject = UserServiceImpl(dao)

    /**
     * Test getting a user by ID when the user doesn't exist
     */
    @Test
    fun getUnknownUserById() {
        every { dao.findById(UUID.fromString(USER_ID)) } returns Optional.empty()

        Assertions.assertThrows(UserNotFoundException::class.java) {
            testSubject.getById(UserId(USER_ID))
        }

        verify { dao.findById(UUID.fromString(USER_ID)) }
    }

    /**
     * Test getting a user by ID when the user does exist
     */
    @Test
    fun getKnownUserById() {
        val userEntity = generateUserEntity()

        every { dao.findById(UUID.fromString(USER_ID)) } returns Optional.of(userEntity)

        val user = testSubject.getById(UserId(USER_ID))

        verifyGeneratedUser(user)
        verify { dao.findById(UUID.fromString(USER_ID)) }
    }

    /**
     * Test getting a user by email when the user doesn't exist
     */
    @Test
    fun getUnknownUserByEmail() {
        every { dao.findByEmailIgnoreCase(EMAIL_ADDRESS) } returns Optional.empty()

        val user = testSubject.getByEmail(EMAIL_ADDRESS)

        Assertions.assertNull(user)
        verify { dao.findByEmailIgnoreCase(EMAIL_ADDRESS) }
    }

    /**
     * Test getting a user by Email when the user does exist
     */
    @Test
    fun getKnownUserByEmail() {
        val userEntity = generateUserEntity()

        every { dao.findByEmailIgnoreCase(EMAIL_ADDRESS) } returns Optional.of(userEntity)

        val user = testSubject.getByEmail(EMAIL_ADDRESS)

        Assertions.assertNotNull(user)
        verifyGeneratedUser(user!!)
        verify { dao.findByEmailIgnoreCase(EMAIL_ADDRESS) }
    }

    /**
     * Generate a User Entity to return from DAO methods
     */
    private fun generateUserEntity(): UserEntity {
        val userEntity = UserEntity(
                id = UUID.fromString(USER_ID),
                version = UUID.fromString("00000000-0000-0000-0000-000000000002"),
                created = Instant.parse("2018-05-22T18:53:00Z"),
                updated = Instant.parse("2018-05-22T18:54:00Z"),
                displayName = "Test User",
                email = EMAIL_ADDRESS,
                passwordHash = Base64.getEncoder().encodeToString("password".toByteArray()),
                paswordSalt = Base64.getEncoder().encodeToString("salt".toByteArray())
        )
        return userEntity
    }

    /**
     * Verify that a User resource matches the details generated by generateUserEntity()
     */
    private fun verifyGeneratedUser(user: Resource<UserId, UserData>) {
        Assertions.assertAll(
                Executable { Assertions.assertEquals(UserId(USER_ID), user.identity.id) },
                Executable { Assertions.assertEquals("00000000-0000-0000-0000-000000000002", user.identity.version) },
                Executable { Assertions.assertEquals(Instant.parse("2018-05-22T18:53:00Z"), user.identity.created) },
                Executable { Assertions.assertEquals(Instant.parse("2018-05-22T18:54:00Z"), user.identity.updated) },

                Executable { Assertions.assertEquals("Test User", user.data.displayName) },
                Executable { Assertions.assertEquals(EMAIL_ADDRESS, user.data.email) },
                Executable { Assertions.assertArrayEquals("password".toByteArray(), user.data.password.hash) },
                Executable { Assertions.assertArrayEquals("salt".toByteArray(), user.data.password.salt) }
        )
    }

}
