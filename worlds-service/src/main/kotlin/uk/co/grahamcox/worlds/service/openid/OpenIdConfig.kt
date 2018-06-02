package uk.co.grahamcox.worlds.service.openid

import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import uk.co.grahamcox.worlds.service.openid.responseTypes.ResponseTypes
import uk.co.grahamcox.worlds.service.openid.token.AccessTokenGeneratorImpl
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
                    "id_token" to setOf(ResponseTypes.ID_TOKEN),
                    "id_token token" to setOf(ResponseTypes.ID_TOKEN, ResponseTypes.TOKEN)
            )

            bean {
                RegisterController(
                        ref(),
                        ref(),
                        supportedResponseTypes
                )
            }
            
            bean {
                StartAuthorizeController(
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
        }.initialize(context)
    }
}
