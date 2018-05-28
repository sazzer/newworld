package uk.co.grahamcox.worlds.service.acceptance.requester

import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.function.Executable
import org.springframework.http.MediaType

/**
 * Configuration of a single field to match
 */
data class HTMLFieldConfig(
        val fieldPath: String
)

/**
 * Means to match the response received from the system
 */
class HTMLMatcher(
        private val requester: Requester,
        private val matchers: Map<String, HTMLFieldConfig>
) {
    /**
     * Check if the last response in the Requester matches the expected values
     */
    fun match(expected: Map<String, String>) {
        val lastResponse = requester.lastResponse

        Assertions.assertTrue(lastResponse.response.headers.contentType!!.isCompatibleWith(MediaType.TEXT_HTML))

        val parsed = Jsoup.parse(lastResponse.response.body as String)

        val assertions = expected
                .onEach {
                    Assertions.assertTrue(matchers.containsKey(it.key), """No matcher registered for property "${it.key}"""")
                }
                .mapKeys { matchers[it.key]!! }
                .toList()
                .map { (field, expected) ->
                    val value = parsed.select(field.fieldPath).text()

                    Executable { Assertions.assertEquals(expected, value) }
                }

        Assertions.assertAll(assertions)
    }
}
