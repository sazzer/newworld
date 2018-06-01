package uk.co.grahamcox.worlds.service.users.password

/**
 * Interface describing how to hash a password
 */
interface PasswordHasher {
    /**
     * Hash a password, generating a salt to use
     * @param password The password to hash
     * @return the hashed password
     */
    fun hashPassword(password: String) : Password

    /**
     * Hash a password, providing the salt to use
     * @param password The password to hash
     * @param salt The salt to use
     * @return the hashed password
     */
    fun hashPassword(password: String, salt: ByteArray) : Password
}
