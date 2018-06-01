package uk.co.grahamcox.worlds.service.openid.scopes

/**
 * Exception to indicate that some scopes that are specified are unknown
 */
class UnknownScopesException(val unknownScopes: Collection<String>) : RuntimeException()
