package uk.co.grahamcox.worlds.service.openid.client

import uk.co.grahamcox.worlds.service.model.Id

/**
 * The ID of an OpenID Client
 */
data class ClientId(override val id: String) : Id
