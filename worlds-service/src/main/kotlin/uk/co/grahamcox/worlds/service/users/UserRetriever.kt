package uk.co.grahamcox.worlds.service.users

import uk.co.grahamcox.worlds.service.model.Resource

/**
 * Interface describing how to retrieve users
 */
interface UserRetriever {
    /**
     * Get a single User by ID
     * @param id The ID of the user
     * @return the user
     */
    fun getById(id: UserId) : Resource<UserId, UserData>

    /**
     * Get a single user with the given email address
     * @param email The email address to look up
     * @return the user, if found
     */
    fun getByEmail(email: String) : Resource<UserId, UserData>?
}
