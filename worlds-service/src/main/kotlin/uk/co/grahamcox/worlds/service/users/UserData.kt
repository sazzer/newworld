package uk.co.grahamcox.worlds.service.users

import uk.co.grahamcox.worlds.service.users.password.Password

/**
 * Representation of the data that relates to a user
 */
data class UserData(
        val email: String,
        val username: String,
        val displayName: String,
        val password: Password
)
