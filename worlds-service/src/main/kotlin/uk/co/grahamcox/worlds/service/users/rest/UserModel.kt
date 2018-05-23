package uk.co.grahamcox.worlds.service.users.rest

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * REST Model class representing a User
 */
data class UserModel(
        val id: String,
        @JsonProperty("display_name") val displayName: String,
        val email: String
)
