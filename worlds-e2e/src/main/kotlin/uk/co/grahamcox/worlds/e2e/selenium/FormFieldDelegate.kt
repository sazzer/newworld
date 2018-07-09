package uk.co.grahamcox.worlds.e2e.selenium

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import kotlin.reflect.KProperty

/**
 * Delegate to allow simple access to selenium form fields
 */
class FormFieldDelegate(private val formElement: WebElement, private val selector: By) {
    /**
     * Get the current value of the form field
     */
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return formElement.findElement(selector).getAttribute("value")
    }

    /**
     * Set a new value for the form field
     */
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        val field = formElement.findElement(selector)
        field.clear()
        field.sendKeys(value)
    }
}
