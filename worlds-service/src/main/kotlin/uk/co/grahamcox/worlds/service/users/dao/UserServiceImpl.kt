package uk.co.grahamcox.worlds.service.users.dao

import org.slf4j.LoggerFactory
import uk.co.grahamcox.worlds.service.model.Identity
import uk.co.grahamcox.worlds.service.model.Resource
import uk.co.grahamcox.worlds.service.users.*
import uk.co.grahamcox.worlds.service.users.password.Password
import java.time.Clock
import java.util.*
import javax.transaction.Transactional

/**
 * Standard implementation of the User Service in terms of the data base
 */
open class UserServiceImpl(
        private val dao: UsersRepository,
        private val clock: Clock
) : UserService {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(UserServiceImpl::class.java)
    }

    /**
     * Get a single User by ID
     * @param id The ID of the user
     * @return the user
     */
    override fun getById(id: UserId): Resource<UserId, UserData> {
        val userEntity = dao.findById(UUID.fromString(id.id))

        val user = userEntity.map(::translateUser).orElseThrow {
            LOG.warn("No user found with ID {}", id)
            UserNotFoundException()
        }

        LOG.debug("Found user {}", user)
        return user
    }

    /**
     * Get a single user with the given email address
     * @param email The email address to look up
     * @return the user, if found
     */
    override fun getByEmail(email: String): Resource<UserId, UserData>? {
        val userEntity = dao.findByEmailIgnoreCase(email)

        val user = userEntity
                .map(::translateUser)
                .orElse(null)
        LOG.debug("Found user {} with email address {}", user, email)
        return user
    }

    /**
     * Create a new user in the system
     * @param user The user to create
     * @return the newly created user
     */
    @Transactional
    override fun create(user: UserData): Resource<UserId, UserData> {
        if (dao.findByUsernameIgnoreCase(user.username).isPresent) {
            throw DuplicateUsernameException()
        }
        
        val userEntity = UserEntity(
                id = UUID.randomUUID(),
                version = UUID.randomUUID(),
                created = clock.instant(),
                updated = clock.instant(),
                email = user.email,
                username = user.username,
                displayName = user.displayName,
                passwordHash = Base64.getEncoder().encodeToString(user.password.hash),
                paswordSalt = Base64.getEncoder().encodeToString(user.password.salt)
        )

        val createdEntity = dao.save(userEntity)

        return translateUser(createdEntity)
    }

    /**
     * Update a user to have the provided data
     * @param userId The ID of the user to update
     * @param user The data to update the user with
     * @return the updated user
     */
    @Transactional
    override fun update(userId: UserId, user: UserData): Resource<UserId, UserData> {
        if (dao.findByUsernameIgnoreCase(user.username).filter { it.id != UUID.fromString(userId.id) }.isPresent) {
            throw DuplicateUsernameException()
        }

        val userEntity = dao.findById(UUID.fromString(userId.id))
                .orElseThrow { UserNotFoundException() }

        userEntity.version = UUID.randomUUID()
        userEntity.updated = clock.instant()
        userEntity.email = user.email
        userEntity.username = user.username
        userEntity.displayName = user.displayName
        userEntity.passwordHash = Base64.getEncoder().encodeToString(user.password.hash)
        userEntity.paswordSalt = Base64.getEncoder().encodeToString(user.password.salt)

        return translateUser(userEntity)
    }

    /**
     * Translate the given user into the internal resource representation
     * @param user The user to translate
     * @return the translated user
     */
    private fun translateUser(user: UserEntity): Resource<UserId, UserData> {
        return Resource(
                identity = Identity(
                        id = UserId(user.id.toString()),
                        version = user.version.toString(),
                        created = user.created,
                        updated = user.updated
                ),
                data = UserData(
                        email = user.email,
                        username = user.username,
                        displayName = user.displayName,
                        password = Password(
                                hash = Base64.getDecoder().decode(user.passwordHash),
                                salt = Base64.getDecoder().decode(user.paswordSalt)
                        )
                )
        )
    }
}
