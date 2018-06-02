package uk.co.grahamcox.worlds.service.openid.webapp

/**
 * Exception to indicate that we've got a Response Type that we don't support
 */
class UnsupportedResponseTypeException(val responseType: String) : RuntimeException()
