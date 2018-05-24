package uk.co.grahamcox.worlds.service.users

import org.slf4j.LoggerFactory
import uk.co.grahamcox.worlds.service.model.Identity
import uk.co.grahamcox.worlds.service.model.Resource
import uk.co.grahamcox.worlds.service.users.dao.UsersRepository
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

        val user = userEntity.map {
            Resource(
                    identity = Identity(
                            id = UserId(it.id.toString()),
                            version = it.version.toString(),
                            created = it.created,
                            updated = it.updated
                    ),
                    data = UserData(
                            email = it.email,
                            displayName = it.displayName,
                            password = Password(
                                    hash = Base64.getDecoder().decode(it.passwordHash),
                                    salt = Base64.getDecoder().decode(it.paswordSalt)
                            )
                    )
            )
        }.orElseThrow {
            LOG.warn("No user found with ID {}", id)
            UserNotFoundException()
        }

        LOG.debug("Found user {}", user)
        return user
    }
}
