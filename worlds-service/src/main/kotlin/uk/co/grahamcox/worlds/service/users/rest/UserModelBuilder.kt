package uk.co.grahamcox.worlds.service.users.rest

import uk.co.grahamcox.worlds.service.model.Resource
import uk.co.grahamcox.worlds.service.openid.rest.AccessTokenHolder
import uk.co.grahamcox.worlds.service.rest.ModelBuilder
import uk.co.grahamcox.worlds.service.users.UserData
import uk.co.grahamcox.worlds.service.users.UserId

/**
 * Helper to build a User Model from an internal User resource
 */
class UserModelBuilder(
        private val accessTokenHolder: AccessTokenHolder
) : ModelBuilder<UserId, UserData, UserModel> {
    /**
     * Build the User Model from the internal resource
     * @param input The input to build the model from
     * @return the model
     */
    override fun build(input: Resource<UserId, UserData>) : UserModel {
        val currentUser = accessTokenHolder.accessToken?.user

        return UserModel(
                id = input.identity.id.id,
                displayName = input.data.displayName,
                email = if (currentUser == input.identity.id) input.data.email else null,
                username = input.data.username
        )
    }
}
