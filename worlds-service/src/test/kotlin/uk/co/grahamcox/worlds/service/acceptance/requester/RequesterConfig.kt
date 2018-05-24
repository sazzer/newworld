package uk.co.grahamcox.worlds.service.acceptance.requester

import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans

/**
 * Spring Config for standard request behaviours
 */
@Configuration
class RequesterConfig(context: GenericApplicationContext) {
    init {
        beans {
            bean<Requester>()

            bean("problemResponseMatcher") {
                ResponseMatcher(
                        ref(),
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
        }.initialize(context)
    }
}
