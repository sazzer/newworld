package uk.co.grahamcox.worlds.service.worlds

import uk.co.grahamcox.worlds.service.model.Id

/**
 * The ID of a World
 */
data class WorldId(override val id: String) : Id
