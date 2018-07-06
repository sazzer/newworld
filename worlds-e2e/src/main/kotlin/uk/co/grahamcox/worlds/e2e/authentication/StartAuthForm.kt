package uk.co.grahamcox.worlds.e2e.authentication

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory

/**
 * Page Model representing the form to start authentication with
 */
class StartAuthForm(base: WebElement) {
    init {
        PageFactory.initElements(DefaultElementLocatorFactory(base), this)
    }

    /** The Web Element that represents the Email Address input box */
    @FindBy(css = "input[name=email]")
    private lateinit var emailInput: WebElement

    /** The Web Element that represents the Submit button */
    @FindBy(css = "button[type=submit]")
    private lateinit var submitButton: WebElement

    /**
     * Populate and submit the form
     */
    fun populateAndSubmit(values: Map<String, String>) {
        emailInput.sendKeys(values["Email Address"])
        submitButton.click()
    }
}
