package uk.co.grahamcox.worlds.service.openid.client

import uk.co.grahamcox.worlds.service.users.UserId
import java.net.URI

/**
 * Data representing an OpenID Connect client
 */
data class ClientData(
        val owner: UserId,
        val redirectUris: Set<URI>,
        val responseTypes: Set<ResponseType>,
        val grantTypes: Set<GrantType>,
        val applicationType: ApplicationType,
        val contacts: List<String>,
        val clientName: String,
        val clientUri: URI?,
        val secret: String
)
