package uk.co.grahamcox.worlds.service.worlds.rest

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * REST Model class representing a World
 */
data class WorldModel(
        val id: String,
        val name: String,
        @JsonProperty("display_name") val displayName: String,
        val description: String,
        val owner: String
)
