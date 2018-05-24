package uk.co.grahamcox.worlds.service.openid.webapp

/**
 * Command object containing the fields we want from OpenID Connect calls to Authorize a user
 */
data class AuthorizeCommand(
        val responseType: String?,
        val scope: String?,
        val clientId: String?,
        val redirectUri: String?,
        val state: String?,
        val nonce: String?
)
