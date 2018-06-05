package uk.co.grahamcox.worlds.service.openid

import com.auth0.jwt.algorithms.Algorithm
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import uk.co.grahamcox.worlds.service.openid.responseTypes.ResponseTypes
import uk.co.grahamcox.worlds.service.openid.rest.AccessTokenArgumentResolver
import uk.co.grahamcox.worlds.service.openid.rest.AccessTokenControllerAdvice
import uk.co.grahamcox.worlds.service.openid.rest.AccessTokenInterceptor
import uk.co.grahamcox.worlds.service.openid.rest.AccessTokenStore
import uk.co.grahamcox.worlds.service.openid.scopes.ScopeRegistry
import uk.co.grahamcox.worlds.service.openid.token.AccessTokenGeneratorImpl
import uk.co.grahamcox.worlds.service.openid.token.JwtAccessTokenSerializerImpl
import uk.co.grahamcox.worlds.service.openid.webapp.LoginController
import uk.co.grahamcox.worlds.service.openid.webapp.RedirectUriBuilder
import uk.co.grahamcox.worlds.service.openid.webapp.RegisterController
import uk.co.grahamcox.worlds.service.openid.webapp.StartAuthorizeController
import java.time.Duration

/**
 * Spring configuration for the OpenID Connect handlers
 */
@Configuration
class OpenIdConfig(context: GenericApplicationContext) {
    init {
        beans {
            // For now this is only the strings that map on to "Implicit Flow"
            // I.e. not anything that uses the "code" flow.
            val supportedResponseTypes = mapOf(
                    "token" to setOf(ResponseTypes.TOKEN),
                    "id_token" to setOf(ResponseTypes.ID_TOKEN),
                    "id_token token" to setOf(ResponseTypes.ID_TOKEN, ResponseTypes.TOKEN)
            )

            bean {
                ScopeRegistry(
                        listOf(
                                OpenIdScopes.values().toList()
                        ).flatten()
                )
            }

            bean {
                RedirectUriBuilder(
                        ref(),
                        ref(),
                        ref(),
                        ref(),
                        supportedResponseTypes
                )
            }

            bean {
                RegisterController(
                        ref(),
                        ref(),
                        ref(),
                        ref(),
                        supportedResponseTypes
                )
            }

            bean {
                LoginController(
                        ref(),
                        ref(),
                        ref(),
                        ref(),
                        supportedResponseTypes
                )
            }

            bean {
                StartAuthorizeController(
                        ref(),
                        ref(),
                        supportedResponseTypes
                )
            }

            bean {
                AccessTokenGeneratorImpl(
                        ref(),
                        Duration.ofDays(365)
                )
            }

            bean {
                JwtAccessTokenSerializerImpl(
                        Algorithm.HMAC512("superSecret"),
                        ref()
                )
            }

            bean<AccessTokenStore>()
            bean<AccessTokenInterceptor>()
            bean<AccessTokenArgumentResolver>()
            bean<AccessTokenControllerAdvice>()
        }.initialize(context)
    }
}
