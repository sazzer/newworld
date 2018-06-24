package uk.co.grahamcox.worlds.service.users

import uk.co.grahamcox.worlds.service.users.password.PasswordHasher

/**
 * Standard implementation of the Password Changer
 */
class PasswordChangerImpl(
        private val userService: UserService,
        private val passwordHasher: PasswordHasher
) : PasswordChanger {
    /**
     * Change the password for the given user
     * @param userId the ID of the user who's password is getting changed
     * @param oldPassword The old password for the user
     * @param newPassword The new password for the user
     */
    override fun changePassword(userId: UserId, oldPassword: String, newPassword: String) {
        val user = userService.getById(userId)

        val hashedOldPassword = passwordHasher.hashPassword(oldPassword, user.data.password.salt)

        if (hashedOldPassword != user.data.password) {
            throw InvalidPasswordException()
        }

        val hashedNewPassword = passwordHasher.hashPassword(newPassword)

        userService.update(userId, user.data.copy(password = hashedNewPassword))
    }
}
