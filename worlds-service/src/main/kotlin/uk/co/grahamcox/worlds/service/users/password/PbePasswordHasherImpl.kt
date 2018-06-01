package uk.co.grahamcox.worlds.service.users.password

import org.slf4j.LoggerFactory
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

/**
 * Standard implementation of the Password Hasher, using a Password Based Encryption mechanism
 */
class PbePasswordHasherImpl(
        hasherName: String,
        private val saltSize: Int,
        private val iterations: Int,
        private val keyLength: Int
) : PasswordHasher {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(PbePasswordHasherImpl::class.java)
    }

    /** The secret key factory to use for generating hashes */
    private val secretKeyFactory = SecretKeyFactory.getInstance(hasherName)

    init {
        // This does nothing more than prove that we can hash passwords
        hashPassword("test")
    }

    /**
     * Hash a password, generating a salt to use
     * @param password The password to hash
     * @return the hashed password
     */
    override fun hashPassword(password: String) = hashPassword(password, generateSalt())

    /**
     * Hash a password, providing the salt to use
     * @param password The password to hash
     * @param salt The salt to use
     * @return the hashed password
     */
    override fun hashPassword(password: String, salt: ByteArray): Password {
        val passwordChars = password.toCharArray()

        try {
            val keySpec = PBEKeySpec(passwordChars, salt, iterations, keyLength)
            val key = secretKeyFactory.generateSecret(keySpec)
            val hash = key.encoded

            return Password(hash, salt)
        } catch (e: InvalidKeySpecException) {
            // This really can't happen because we already verified the parameters in the constructor
            LOG.error("Invalid configuration for hashing algorithm", e)
            throw PasswordHasherException("Invalid configuration for hashing algorithm", e)
        }
    }

    /**
     * Generate the salt to use for the password
     * @return the generated salt
     */
    private fun generateSalt(): ByteArray {
        val r = SecureRandom()
        val salt = ByteArray(saltSize)
        r.nextBytes(salt)

        return salt
    }
}
