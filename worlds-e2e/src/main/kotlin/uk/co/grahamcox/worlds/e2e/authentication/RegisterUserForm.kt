package uk.co.grahamcox.worlds.e2e.authentication

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory

/**
 * Page Model representing the form to register with
 */
class RegisterUserForm(base: WebElement) {
    init {
        PageFactory.initElements(DefaultElementLocatorFactory(base), this)
    }

    /** The Web Element that represents the Password input box */
    @FindBy(css = "input[name=password]")
    private lateinit var passwordInput: WebElement

    /** The Web Element that represents the Confirm Password input box */
    @FindBy(css = "input[name=password2]")
    private lateinit var password2Input: WebElement

    /** The Web Element that represents the Username input box */
    @FindBy(css = "input[name=username]")
    private lateinit var usernameInput: WebElement

    /** The Web Element that represents the Display Name input box */
    @FindBy(css = "input[name=display_name]")
    private lateinit var displayNameInput: WebElement

    /** The Web Element that represents the Submit button */
    @FindBy(css = "button[type=submit]")
    private lateinit var submitButton: WebElement

    /**
     * Populate and submit the form
     */
    fun populateAndSubmit(values: Map<String, String>) {
        passwordInput.sendKeys(values["Password"])
        password2Input.sendKeys(values["Confirm Password"])
        usernameInput.sendKeys(values["Username"])
        displayNameInput.sendKeys(values["Display Name"])

        submitButton.click()
    }
}
