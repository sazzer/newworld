package uk.co.grahamcox.worlds.service.users.dao

import org.springframework.data.repository.CrudRepository
import java.util.*

/**
 * Spring Data Repository representing the Users data
 */
interface UsersRepository : CrudRepository<UserEntity, UUID> {
    /**
     * Find the user with the given email address, if there is one
     */
    fun findByEmailIgnoreCase(email: String) : Optional<UserEntity>

    /**
     * Test whether the given username exists or not
     * @param username The username to test
     * @return True if the username exists. False if not
     */
    fun existsByUsernameIgnoreCase(username: String) : Boolean
}
