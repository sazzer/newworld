package uk.co.grahamcox.worlds.service.users

import org.springframework.data.repository.CrudRepository

/**
 * Spring Data Repository representing the Users data
 */
interface UsersRepository : CrudRepository<UserEntity, String>
