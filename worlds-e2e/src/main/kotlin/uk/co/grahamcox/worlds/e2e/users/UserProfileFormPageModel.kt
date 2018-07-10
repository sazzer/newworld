package uk.co.grahamcox.worlds.e2e.users

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory
import uk.co.grahamcox.worlds.e2e.browser.safeIsVisible
import uk.co.grahamcox.worlds.e2e.selenium.FormFieldDelegate

/**
 * Representation of the actual user profile form
 */
class UserProfileFormPageModel(element: WebElement) {
    init {
        PageFactory.initElements(DefaultElementLocatorFactory(element), this)
    }

    /** The email address */
    var email: String by FormFieldDelegate(element, By.name("email"))

    /** The username */
    var username: String by FormFieldDelegate(element, By.name("username"))

    /** The display name */
    var displayName: String by FormFieldDelegate(element, By.name("displayName"))

    /** The actual form */
    private val form = element

    /** The submit button */
    @FindBy(css = "button.primary")
    private lateinit var submit: WebElement

    /** The error message */
    @FindBy(css = "div.error")
    private lateinit var errorMessage: WebElement

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
     * The text of the error message
     */
    val errorText: String
        get() = errorMessage.text


}
