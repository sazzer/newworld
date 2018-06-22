package uk.co.grahamcox.worlds.service.users.rest

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Input model for updating a user
 */
data class UserInputModel(
        @JsonProperty("display_name") val displayName: String,
        val email: String,
        val username: String
)
