package uk.co.grahamcox.worlds.service.openid.webapp

/**
 * Command object containing the fields we want from OpenID Connect calls to Authorize a user
 */
data class AuthorizeCommand(
        private val response_type: String?,
        val scope: String?,
        private val client_id: String?,
        private val redirect_uri: String?,
        val state: String?,
        val nonce: String?
) {
    val responseType = response_type
    val clientId = client_id
    val redirectUri = redirect_uri
}
