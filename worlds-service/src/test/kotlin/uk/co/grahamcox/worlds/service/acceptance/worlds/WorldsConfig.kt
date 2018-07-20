package uk.co.grahamcox.worlds.service.acceptance.worlds

import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.http.HttpMethod
import org.springframework.web.util.UriComponentsBuilder
import uk.co.grahamcox.worlds.service.acceptance.database.DatabaseExistsVerifier
import uk.co.grahamcox.worlds.service.acceptance.requester.RequestFieldConfig
import uk.co.grahamcox.worlds.service.acceptance.requester.RequestSubmitter
import uk.co.grahamcox.worlds.service.acceptance.requester.ResponseFieldConfig
import uk.co.grahamcox.worlds.service.acceptance.requester.ResponseMatcher
import uk.co.grahamcox.worlds.service.acceptance.stripUri

/**
 * Spring Configuration for testing of worlds
 */
@Configuration
class WorldsConfig(context: GenericApplicationContext) {
    init {
        beans {
            bean("worldResponseMatcher") {
                ResponseMatcher(
                        ref(),
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
                                "Name" to ResponseFieldConfig(
                                        fieldPath = "body/name"
                                ),
                                "Display Name" to ResponseFieldConfig(
                                        fieldPath = "body/display_name"
                                ),
                                "Description" to ResponseFieldConfig(
                                        fieldPath = "body/description"
                                ),
                                "Owner" to ResponseFieldConfig(
                                        fieldPath = "body/owner"
                                ),
                                "Self Link" to ResponseFieldConfig(
                                        fieldPath = "body/_links/self/href",
                                        actualConversion = ::stripUri
                                ),
                                "Owner Link" to ResponseFieldConfig(
                                        fieldPath = "body/_links[@name='tag:grahamcox.co.uk,2018,links/world/owner']/href",
                                        actualConversion = ::stripUri
                                )
                        ))
            }

            bean("worldRetrievalRequestSubmitter") {
                RequestSubmitter(
                        ref(),
                        "/api/worlds/{id}",
                        HttpMethod.GET,
                        mapOf(
                                "ID" to RequestFieldConfig(
                                        fieldName = "id"
                                )
                        ),
                        mapOf()
                )
            }
        }.initialize(context)
    }
}
