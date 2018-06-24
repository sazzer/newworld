package uk.co.grahamcox.worlds.service.users

/**
 * Interface describing how to change the password for a user
 */
interface PasswordChanger {
    /**
     * Change the password for the given user
     * @param userId the ID of the user who's password is getting changed
     * @param oldPassword The old password for the user
     * @param newPassword The new password for the user
     */
    fun changePassword(userId: UserId, oldPassword: String, newPassword: String)
}
