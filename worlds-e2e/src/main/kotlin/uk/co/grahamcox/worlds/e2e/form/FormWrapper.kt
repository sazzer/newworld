package uk.co.grahamcox.worlds.e2e.form

import org.assertj.core.api.Assertions
import org.slf4j.LoggerFactory
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1

/**
 * Definition of a single field to work with
 */
data class FormField<FORM>(
        val property: KProperty1<FORM, String>
) {
    val mutable: Boolean = property is KMutableProperty1<FORM, String>
}

/**
 * Wrapper around a Form to read and update
 */
class FormWrapper<in FORM>(
        private val fields: Map<String, FormField<FORM>>
) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(FormWrapper::class.java)
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
            val realValue = formField.property.get(form)
            LOG.debug("Real value for form field {}: {}", key, realValue)

            Assertions.assertThat(realValue)
                    .isEqualTo(value)
        }
    }

    /**
     * Update the given form to the provided values
     */
    fun update(form: FORM, target: Map<String, String>) {
        val unexpected = target.keys
                .filterNot(fields::containsKey)
                .filter { fields[it]!!.mutable }

        Assertions.assertThat(unexpected)
                .`as`("Unexpected form fields")
                .isEmpty()

        target.forEach { key, value ->
            val formField = fields[key]!!
            LOG.debug("Setting value for form field {}: {}", key, value)
            formField.property as KMutableProperty1
            formField.property.set(form, value)
        }
    }
}
