package uk.co.grahamcox.worlds.service.openid

import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import uk.co.grahamcox.worlds.service.openid.webapp.AuthorizeController

/**
 * Spring configuration for the OpenID Connect handlers
 */
@Configuration
class OpenIdConfig(context: GenericApplicationContext) {
    init {
        beans {
            bean<AuthorizeController>()
        }.initialize(context)
    }
}
