package uk.co.grahamcox.worlds.service.users

/**
 * Exception to indicate that a username already exists
 */
class DuplicateUsernameException : RuntimeException("Requested username already exists")
