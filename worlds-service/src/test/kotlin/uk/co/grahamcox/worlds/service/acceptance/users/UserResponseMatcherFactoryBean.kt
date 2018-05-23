package uk.co.grahamcox.worlds.service.acceptance.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.AbstractFactoryBean
import org.springframework.stereotype.Component
import uk.co.grahamcox.worlds.service.acceptance.requester.Requester
import uk.co.grahamcox.worlds.service.acceptance.requester.ResponseFieldConfig
import uk.co.grahamcox.worlds.service.acceptance.requester.ResponseMatcher

/**
 * Construct the Response Matcher to use for matching User Responses
 */
@Component("userResponseMatcher")
class UserResponseMatcherFactoryBean : AbstractFactoryBean<ResponseMatcher>() {
    /** The requester to use */
    @Autowired
    private lateinit var requester: Requester

    override fun getObjectType() = ResponseMatcher::class.java

    override fun createInstance(): ResponseMatcher {
        return ResponseMatcher(
                requester,
                mapOf(
                        "ID" to ResponseFieldConfig(
                                fieldPath = "body/id"
                        ),
                        "ETag" to ResponseFieldConfig(
                                fieldPath = "headers/etag",
                                expectedConversion = { listOf(it) }
                        ),
                        "Last Modified" to ResponseFieldConfig(
                                fieldPath = "headers/last-modified",
                                expectedConversion = { listOf(it) }
                        ),
                        "Email" to ResponseFieldConfig(
                                fieldPath = "body/email"
                        ),
                        "Display Name" to ResponseFieldConfig(
                                fieldPath = "body/display_name"
                        )
                ))
    }

}
