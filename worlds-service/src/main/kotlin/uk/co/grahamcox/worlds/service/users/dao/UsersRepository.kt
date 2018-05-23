package uk.co.grahamcox.worlds.service.users.dao

import org.springframework.data.repository.CrudRepository
import java.util.*

/**
 * Spring Data Repository representing the Users data
 */
interface UsersRepository : CrudRepository<UserEntity, UUID>
