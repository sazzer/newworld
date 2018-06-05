package uk.co.grahamcox.worlds.service.openid.rest

/**
 * Exception to indicate that a method required an access token but it wasn't available
 */
class MissingAccessTokenException : RuntimeException()
