package uk.co.grahamcox.worlds.service.openid

import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import uk.co.grahamcox.worlds.service.openid.token.AccessTokenGeneratorImpl
import uk.co.grahamcox.worlds.service.openid.webapp.AuthorizeController
import java.time.Duration

/**
 * Spring configuration for the OpenID Connect handlers
 */
@Configuration
class OpenIdConfig(context: GenericApplicationContext) {
    init {
        beans {
            bean<AuthorizeController>()

            bean {
                AccessTokenGeneratorImpl(
                        ref(),
                        Duration.ofDays(365)
                )
            }
        }.initialize(context)
    }
}
