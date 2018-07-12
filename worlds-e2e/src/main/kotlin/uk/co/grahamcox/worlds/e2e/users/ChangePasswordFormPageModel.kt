package uk.co.grahamcox.worlds.e2e.users

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory
import uk.co.grahamcox.worlds.e2e.browser.safeIsVisible
import uk.co.grahamcox.worlds.e2e.selenium.FormFieldDelegate

/**
 * Representation of the actual change password form
 */
class ChangePasswordFormPageModel(element: WebElement) {
    init {
        PageFactory.initElements(DefaultElementLocatorFactory(element), this)
    }

    /** The old password */
    var oldPassword: String by FormFieldDelegate(element, By.name("original"))

    /** The new password */
    var newPassword: String by FormFieldDelegate(element, By.name("new1"))

    /** The repeated new password */
    var repeatPassword: String by FormFieldDelegate(element, By.name("new2"))

    /** The actual form */
    private val form = element

    /** The submit button */
    @FindBy(css = "button.primary")
    private lateinit var submit: WebElement

    /** The error message */
    @FindBy(css = "div.error")
    private lateinit var errorMessage: WebElement

    /** The success message */
    @FindBy(css = "div.success")
    private lateinit var successMessage: WebElement

    /**
     * Save the changes to the form
     */
    fun save() {
        submit.click()
    }

    /**
     * Whether or not the form is in a Loading state
     */
    val loading: Boolean
        get() = form.getAttribute("class")
                .split(" ")
                .contains("loading")

    /**
     * Whether or not the form is visible
     */
    val visible: Boolean
        get() = form.safeIsVisible

    /**
     * Whether the error message is visible or not
     */
    val errorVisible: Boolean
        get() = errorMessage.safeIsVisible

    /**
     * Whether the success message is visible or not
     */
    val successVisible: Boolean
        get() = successMessage.safeIsVisible

    /**
     * The text of the error message
     */
    val errorText: String
        get() = errorMessage.text


}
