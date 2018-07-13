package uk.co.grahamcox.worlds.service.worlds.dao

import org.slf4j.LoggerFactory
import uk.co.grahamcox.worlds.service.model.Identity
import uk.co.grahamcox.worlds.service.model.Resource
import uk.co.grahamcox.worlds.service.users.UserId
import uk.co.grahamcox.worlds.service.worlds.WorldData
import uk.co.grahamcox.worlds.service.worlds.WorldId
import uk.co.grahamcox.worlds.service.worlds.WorldNotFoundException
import uk.co.grahamcox.worlds.service.worlds.WorldService
import java.util.*

/**
 * Standard implementation of the World Service in terms of the DAO layer
 */
class WorldServiceImpl(
        private val dao: WorldsRepository
) : WorldService {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(WorldServiceImpl::class.java)
    }

    /**
     * Get a single World by ID
     * @param id The ID of the world
     * @return the world
     */
    override fun getById(id: WorldId): Resource<WorldId, WorldData> {
        LOG.debug("Loading world with ID: {}", id)
        
        val worldEntity = dao.findById(UUID.fromString(id.id))
        val world = worldEntity.map(::translateWorld).orElseThrow {
            LOG.warn("No world found with ID {}", id)
            WorldNotFoundException()
        }

        LOG.debug("Found world {}", world)
        return world
    }

    /**
     * Translate the given world into the internal resource representation
     * @param world The world to translate
     * @return the translated world
     */
    private fun translateWorld(world: WorldEntity): Resource<WorldId, WorldData> {
        return Resource(
                identity = Identity(
                        id = WorldId(world.id.toString()),
                        version = world.version.toString(),
                        created = world.created,
                        updated = world.updated
                ),
                data = WorldData(
                        name = world.name,
                        displayName = world.displayName,
                        description = world.description,
                        owner = UserId(world.owner.toString())
                )
        )
    }
}
