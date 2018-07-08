package uk.co.grahamcox.worlds.e2e.authentication

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory

/**
 * Page Model representing the form to log in with
 */
class LoginUserForm(base: WebElement) {
    init {
        PageFactory.initElements(DefaultElementLocatorFactory(base), this)
    }

    /** The Web Element that represents the Password input box */
    @FindBy(css = "input[name=password]")
    private lateinit var passwordInput: WebElement

    /** The Web Element that represents the Submit button */
    @FindBy(css = "button[type=submit]")
    private lateinit var submitButton: WebElement

    /**
     * Populate and submit the form
     */
    fun populateAndSubmit(values: Map<String, String>) {
        passwordInput.sendKeys(values["Password"])
        submitButton.click()
    }
}
