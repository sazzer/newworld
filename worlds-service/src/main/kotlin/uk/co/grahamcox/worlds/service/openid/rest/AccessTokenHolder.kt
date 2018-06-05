package uk.co.grahamcox.worlds.service.openid.rest

import uk.co.grahamcox.worlds.service.openid.token.AccessToken

/**
 * Interface describing how to get hold of the Access Token for the current request
 */
interface AccessTokenHolder {
    /** The access token */
    val accessToken: AccessToken?
}
