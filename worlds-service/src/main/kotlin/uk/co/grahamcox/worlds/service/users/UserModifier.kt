package uk.co.grahamcox.worlds.service.users

import uk.co.grahamcox.worlds.service.model.Resource

/**
 * Interface describing how to modify user records
 */
interface UserModifier {
    /**
     * Create a new user in the system
     * @param user The user to create
     * @return the newly created user
     */
    fun create(user: UserData) : Resource<UserId, UserData>
}
