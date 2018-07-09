package uk.co.grahamcox.worlds.e2e.matcher

import org.assertj.core.api.Assertions
import org.slf4j.LoggerFactory
import kotlin.reflect.KProperty1

/**
 * Definition of a single field to match
 */
data class FormField<FORM>(
        val getter: KProperty1<FORM, *>
)

/**
 * Matcher to match a form to what we expect to see
 */
class FormMatcher<FORM>(
        val fields: Map<String, FormField<FORM>>
) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(FormMatcher::class.java)
    }
    /**
     * Match the given form to the expected values
     */
    fun match(form: FORM, expected: Map<String, String>) {
        val unexpected = expected.keys
                .filterNot(fields::containsKey)
        Assertions.assertThat(unexpected)
                .`as`("Unexpected form fields")
                .isEmpty()

        expected.forEach { key, value ->
            val formField = fields[key]!!
            val realValue = formField.getter.get(form)
            LOG.debug("Real value for form field {}: {}", key, realValue)

            Assertions.assertThat(realValue)
                    .isEqualTo(value)
        }
    }
}
