package uk.co.grahamcox.worlds.service.users.rest

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Input model describing how to change the password for a user
 */
data class ChangePasswordInputModel(
        @JsonProperty("old_password") val oldPassword: String,
        @JsonProperty("new_password") val newPassword: String
)
