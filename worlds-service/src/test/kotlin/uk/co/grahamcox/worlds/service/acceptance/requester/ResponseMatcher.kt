package uk.co.grahamcox.worlds.service.acceptance.requester

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.function.Executable

/**
 * Configuration of a single field to match
 */
data class ResponseFieldConfig(
        val fieldPath: String,
        val expectedConversion: (String) -> Any = { it -> it }
)

/**
 * Means to match the response received from the system
 */
class ResponseMatcher(
        private val requester: Requester,
        private val matchers: Map<String, ResponseFieldConfig>
) {
    /**
     * Check if the last response in the Requester matches the expected values
     */
    fun match(expected: Map<String, String>) {
        val lastResponse = requester.lastResponse

        val assertions = expected
                .onEach {
                    Assertions.assertTrue(matchers.containsKey(it.key), """No matcher registered for property "${it.key}"""")
                }
                .mapKeys { matchers[it.key]!! }
                .toList()
                .map { (field, expected) ->
                    val value = lastResponse.context.getValue(field.fieldPath)
                    val realExpected = field.expectedConversion(expected)

                    Executable { Assertions.assertEquals(realExpected, value) }
                }

        Assertions.assertAll(assertions)
    }
}
