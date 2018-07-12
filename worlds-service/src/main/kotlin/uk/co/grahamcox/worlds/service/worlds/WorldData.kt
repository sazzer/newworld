package uk.co.grahamcox.worlds.service.worlds

import uk.co.grahamcox.worlds.service.users.UserId

/**
 * The data that represents a world
 */
data class WorldData(
        val name: String,
        val displayName: String,
        val description: String,
        val owner: UserId
)
