package uk.co.grahamcox.worlds.service.worlds.rest

import com.fasterxml.jackson.annotation.JsonProperty
import uk.co.grahamcox.worlds.service.rest.hal.Link

/**
 * Links relevant to a World resource
 */
data class WorldModelLinks(
        val self: Link,
        @JsonProperty("tag:grahamcox.co.uk,2018,links/world/owner") val owner: Link
)

/**
 * REST Model class representing a World
 */
data class WorldModel(
        val id: String,
        val name: String,
        @JsonProperty("display_name") val displayName: String,
        val description: String,
        val owner: String,

        @JsonProperty("_links") val links: WorldModelLinks
)
