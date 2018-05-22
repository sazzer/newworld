package uk.co.grahamcox.worlds.service.users.dao

import org.springframework.data.repository.CrudRepository

/**
 * Spring Data Repository representing the Users data
 */
interface UsersRepository : CrudRepository<UserEntity, String>
