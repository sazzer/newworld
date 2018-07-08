package uk.co.grahamcox.worlds.e2e.authentication

import org.openqa.selenium.NotFoundException
import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory

/**
 * Page Model to represent the screen for logging in an existing user
 */
class LoginPageModel(base: SearchContext) {
    init {
        PageFactory.initElements(DefaultElementLocatorFactory(base), this)

        try {
            form.isDisplayed
        } catch (e: NotFoundException) {
            throw IllegalStateException("Login Form not present on page", e)
        }
    }

    /** The Web Element that represents the actual form */
    @FindBy(css = "form[data-test=login]")
    private lateinit var form: WebElement

    /**
     * Get the form for logging in
     */
    val loginUserForm: LoginUserForm
    get() {
        return LoginUserForm(form)
    }
}
