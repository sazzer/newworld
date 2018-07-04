package uk.co.grahamcox.worlds.service.users

/**
 * Exception to indicate that an email address already exists
 */
class DuplicateEmailException : RuntimeException("Requested email address already exists")
