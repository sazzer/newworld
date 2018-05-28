package uk.co.grahamcox.worlds.service.acceptance.openid

import org.jsoup.select.Elements
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

            bean("openidEmailEntryScreenMatcher") {
                val valueExtractor = { e: Elements -> e.`val`() }

                HTMLMatcher(
                        ref(),
                        mapOf(
                                "response_type" to HTMLFieldConfig("input[name=response_type]", valueExtractor),
                                "scope" to HTMLFieldConfig("input[name=scope]", valueExtractor),
                                "client_id" to HTMLFieldConfig("input[name=client_id]", valueExtractor),
                                "redirect_uri" to HTMLFieldConfig("input[name=redirect_uri]", valueExtractor),
                                "state" to HTMLFieldConfig("input[name=state]", valueExtractor),
                                "nonce" to HTMLFieldConfig("input[name=nonce]", valueExtractor)
                        )
                )
            }
        }.initialize(context)
    }
}
