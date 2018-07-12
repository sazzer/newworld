package uk.co.grahamcox.worlds.service.worlds.dao

import org.springframework.data.repository.CrudRepository
import java.util.*

/**
 * Spring Data Repository representing the Worlds data
 */
interface WorldsRepository : CrudRepository<WorldEntity, UUID>
