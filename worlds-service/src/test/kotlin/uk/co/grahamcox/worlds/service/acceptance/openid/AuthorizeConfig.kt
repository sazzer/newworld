package uk.co.grahamcox.worlds.service.acceptance.openid

import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import uk.co.grahamcox.worlds.service.acceptance.requester.HTMLFieldConfig
import uk.co.grahamcox.worlds.service.acceptance.requester.HTMLMatcher

@Configuration
class AuthorizeConfig(context: GenericApplicationContext) {
    init {
        beans {
            bean("openidAuthorizeErrorMatcher") {
                HTMLMatcher(
                        ref(),
                        mapOf(
                                "Error Message" to HTMLFieldConfig(".wt-error")
                        )
                )
            }
        }.initialize(context)
    }
}
