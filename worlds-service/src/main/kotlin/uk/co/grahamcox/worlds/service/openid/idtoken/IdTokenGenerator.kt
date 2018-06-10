package uk.co.grahamcox.worlds.service.openid.idtoken

import uk.co.grahamcox.worlds.service.model.Resource
import uk.co.grahamcox.worlds.service.openid.client.ClientId
import uk.co.grahamcox.worlds.service.users.UserData
import uk.co.grahamcox.worlds.service.users.UserId

/**
 * Mechanism to generate an ID Token to send to the client.
 * This generates an already serialized ID Token String, because there is no use to the actual contents inside the service
 * layer.
 */
interface IdTokenGenerator {
    /**
     * Generate the ID Token for the given User, Client and with the provided Nonce
     * @param user The user that the ID Token represents
     * @param client The ID of the Client that the ID Token is for
     * @param nonce The nonce of the ID Token
     */
    fun generate(user: Resource<UserId, UserData>, client: ClientId, nonce: String?) : String
}
