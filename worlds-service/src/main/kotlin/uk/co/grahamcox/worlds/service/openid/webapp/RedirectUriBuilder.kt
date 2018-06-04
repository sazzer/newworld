package uk.co.grahamcox.worlds.service.openid.webapp

import org.springframework.web.util.UriComponentsBuilder
import uk.co.grahamcox.worlds.service.model.Resource
import uk.co.grahamcox.worlds.service.openid.responseTypes.ResponseTypes
import uk.co.grahamcox.worlds.service.openid.scopes.ScopeRegistry
import uk.co.grahamcox.worlds.service.openid.token.AccessTokenGenerator
import uk.co.grahamcox.worlds.service.openid.token.AccessTokenSerializer
import uk.co.grahamcox.worlds.service.users.UserData
import uk.co.grahamcox.worlds.service.users.UserId
import java.net.URI
import java.time.Clock
import java.time.Duration

/**
 * Means to build the URI to redirect the caller back to after a successful authorization process
 */
class RedirectUriBuilder(
        private val clock: Clock,
        private val accessTokenGenerator: AccessTokenGenerator,
        private val accessTokenSerializer: AccessTokenSerializer,
        private val scopeRegistry: ScopeRegistry,
        private val supportedResponseTypes: Map<String, Set<ResponseTypes>>
) {
    /**
     * Build the URI to redirect the user to
     * @param command The authorize command object
     * @param user The user to redirect for
     * @return the URI
     *
     */
    fun buildUri(command: AuthorizeCommand,
                 user: Resource<UserId, UserData>): URI {
        val redirectUri = UriComponentsBuilder.fromUriString(command.redirectUri!!)
                .queryParam("state", command.state)

        val responseTypes = supportedResponseTypes[command.responseType] ?: emptySet()

        if (responseTypes.contains(ResponseTypes.TOKEN)) {
            val accessToken = accessTokenGenerator.generate(user.identity.id,
                    scopeRegistry.parseScopeString(command.scope!!))
            val serialized = accessTokenSerializer.serialize(accessToken)

            val now = clock.instant()
            val duration = Duration.between(now, accessToken.expires)
                    .seconds

            redirectUri.queryParam("token_type", "Bearer")
            redirectUri.queryParam("access_token", serialized)
            redirectUri.queryParam("expires_in", duration)
        }

        redirectUri.fragment(redirectUri.build().query)
        redirectUri.query(null)

        return redirectUri.build().toUri()
    }
}
