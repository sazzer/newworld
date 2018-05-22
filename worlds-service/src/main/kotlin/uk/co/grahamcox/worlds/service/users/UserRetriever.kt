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
}
