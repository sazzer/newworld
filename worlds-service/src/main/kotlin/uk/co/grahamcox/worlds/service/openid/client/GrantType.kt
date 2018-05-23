package uk.co.grahamcox.worlds.service.openid.client

/**
 * Enumeration of supported grant types.
 * Note that these include two that aren't covered in OpenID Connect but are in OAuth 2.0.
 */
enum class GrantType {
    AUTHORIZATION_CODE,
    IMPLICIT,
    REFRESH_TOKEN,
    CLIENT_CREDENTIALS,
    PASSWORD
}
