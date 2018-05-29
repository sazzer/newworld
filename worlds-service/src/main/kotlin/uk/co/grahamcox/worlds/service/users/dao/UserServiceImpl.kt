package uk.co.grahamcox.worlds.service.users.dao

import org.slf4j.LoggerFactory
import uk.co.grahamcox.worlds.service.model.Identity
import uk.co.grahamcox.worlds.service.model.Resource
import uk.co.grahamcox.worlds.service.users.*
import java.util.*

/**
 * Standard implementation of the User Retriever in terms of the data base
 */
class UserServiceImpl(private val dao: UsersRepository) : UserRetriever {
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
