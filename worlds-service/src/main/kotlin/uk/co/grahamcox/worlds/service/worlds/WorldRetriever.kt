package uk.co.grahamcox.worlds.service.worlds

import uk.co.grahamcox.worlds.service.model.Resource

/**
 * Interface describing how to retrieve worlds
 */
interface WorldRetriever {
    /**
     * Get a single World by ID
     * @param id The ID of the world
     * @return the world
     */
    fun getById(id: WorldId) : Resource<WorldId, WorldData>
}
