package uk.co.grahamcox.worlds.service.users.password

/**
 * Exception to indicate that an error occurred hashing a password
 */
class PasswordHasherException(message: String, cause: Throwable?) : RuntimeException(message, cause)
