package uk.co.grahamcox.worlds.service.acceptance.openid

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.function.Executable
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.web.util.UriComponentsBuilder
import uk.co.grahamcox.worlds.service.acceptance.database.DatabaseExistsVerifier
import uk.co.grahamcox.worlds.service.acceptance.database.DatabaseSeeder
import uk.co.grahamcox.worlds.service.acceptance.database.SeedFieldConfig
import uk.co.grahamcox.worlds.service.acceptance.requester.Requester
import uk.co.grahamcox.worlds.service.acceptance.requester.ResponseFieldConfig
import uk.co.grahamcox.worlds.service.acceptance.requester.ResponseMatcher
import java.time.Clock
import java.time.Instant
import java.util.*

/**
 * Spring Configuration for testing of authorization
 */
@Configuration
class AuthorizeConfig(context: GenericApplicationContext) {
    init {
        beans {
            bean("authRedirectMatcher") {
                fun convertToUriComponents(input: Any?) = (input as List<String>)
                        .first()
                        .let { UriComponentsBuilder.fromUriString(it).build() }

                fun getUriFragmentValue(input: Any?, key: String): String? {
                    val uriComponents = convertToUriComponents(input)
                    return UriComponentsBuilder.newInstance()
                            .query(uriComponents.fragment)
                            .build()
                            .queryParams[key]
                            ?.firstOrNull()
                }

                ResponseMatcher(
                        ref(),
                        mapOf(
                                "URL Scheme" to ResponseFieldConfig(
                                        fieldPath = "headers/location",
                                        actualConversion = { url -> convertToUriComponents(url).scheme }
                                ),
                                "URL Host" to ResponseFieldConfig(
                                        fieldPath = "headers/location",
                                        actualConversion = { url -> convertToUriComponents(url).host }
                                ),
                                "Query String Present" to ResponseFieldConfig(
                                        fieldPath = "headers/location",
                                        actualConversion = { url -> convertToUriComponents(url).query },
                                        comparison = { expected, actual ->
                                            when(expected.toString().toUpperCase()) {
                                                "YES" -> Assertions.assertNotNull(actual)
                                                "NO" -> Assertions.assertNull(actual)
                                                else -> Assertions.fail("Expected must be Yes or No")
                                            }
                                        }
                                ),
                                "Access Token Fragment" to ResponseFieldConfig(
                                        fieldPath = "headers/location",
                                        actualConversion = { url -> getUriFragmentValue(url, "access_token") },
                                        comparison = { expected, actual ->
                                            when(expected.toString().toUpperCase()) {
                                                "PRESENT" -> Assertions.assertNotNull(actual)
                                                "ABSENT" -> Assertions.assertNull(actual)
                                                else -> Assertions.fail("Expected must be Present or Absent")
                                            }
                                        }
                                ),
                                "Token Type Fragment" to ResponseFieldConfig(
                                        fieldPath = "headers/location",
                                        actualConversion = { url -> getUriFragmentValue(url, "token_type") }
                                ),
                                "Expires In Fragment" to ResponseFieldConfig(
                                        fieldPath = "headers/location",
                                        expectedConversion = { it.toLong() },
                                        actualConversion = { url -> getUriFragmentValue(url, "expires_in")?.toLong() },
                                        comparison = { expected, actual ->
                                            expected as Long
                                            actual as Long

                                            val lowerBound = expected - 10
                                            val upperBound = expected + 10

                                            Assertions.assertAll(
                                                    Executable { Assertions.assertTrue(lowerBound < actual) },
                                                    Executable { Assertions.assertTrue(actual < upperBound) }
                                            )
                                        }
                                ),
                                "State Fragment" to ResponseFieldConfig(
                                        fieldPath = "headers/location",
                                        actualConversion = { url -> getUriFragmentValue(url, "state") }
                                )
                        ))
            }

        }.initialize(context)
    }
}
