package uk.co.grahamcox.worlds.service.users.rest

import com.fasterxml.jackson.annotation.JsonProperty
import uk.co.grahamcox.worlds.service.rest.hal.Link

/**
 * Links relevant to a user
 */
data class UserModelLinks(
        val self: Link,
        @JsonProperty("tag:grahamcox.co.uk,2018,links/user/password") val changePassword: Link?
)

/**
 * REST Model class representing a User
 */
data class UserModel(
        val id: String,
        @JsonProperty("display_name") val displayName: String,
        val email: String?,
        val username: String,

        @JsonProperty("_links") val links: UserModelLinks
)
