package uk.co.grahamcox.worlds.service.acceptance.requester

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.AbstractFactoryBean
import org.springframework.stereotype.Component

/**
 * Construct the Response Matcher to use for matching Problem Responses
 */
@Component("problemResponseMatcher")
class ProblemResponseMatcherFactoryBean : AbstractFactoryBean<ResponseMatcher>() {
    /** The requester to use */
    @Autowired
    private lateinit var requester: Requester

    override fun getObjectType() = ResponseMatcher::class.java

    override fun createInstance(): ResponseMatcher {
        return ResponseMatcher(
                requester,
                mapOf(
                        "Type" to ResponseFieldConfig(
                                fieldPath = "body/type"
                        ),
                        "Title" to ResponseFieldConfig(
                                fieldPath = "body/title"
                        ),
                        "Status" to ResponseFieldConfig(
                                fieldPath = "body/status",
                                expectedConversion = { it.toInt() }
                        )
                ))
    }

}
