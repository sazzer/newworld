package uk.co.grahamcox.worlds.service.users

import uk.co.grahamcox.worlds.service.model.Id

/**
 * The ID of a User
 */
data class UserId(override val id: String) : Id
