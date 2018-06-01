package uk.co.grahamcox.worlds.service.openid

import uk.co.grahamcox.worlds.service.openid.scopes.Scope

/**
 * Scopes that are specific to the OpenID Connect system
 */
enum class OpenIdScopes(override val id: String) : Scope {
    OPENID("openid"),
    EMAIL("email"),
    PROFILE("profile")
}
