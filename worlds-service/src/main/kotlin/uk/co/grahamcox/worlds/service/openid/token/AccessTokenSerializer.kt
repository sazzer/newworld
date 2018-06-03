package uk.co.grahamcox.worlds.service.openid.token

/**
 * Mechanism to serialize an Access Token to a string and back
 */
interface AccessTokenSerializer {
    /**
     * Serialize an access token into a string
     * @param accessToken The access token to serialize
     * @return the serialized access token
     */
    fun serialize(accessToken: AccessToken) : String

    /**
     * Deserialize an access token from a string
     * @param accessToken The access token to deserialize
     * @return the deserialized access token
     */
    fun deserialize(accessToken: String) : AccessToken
}
