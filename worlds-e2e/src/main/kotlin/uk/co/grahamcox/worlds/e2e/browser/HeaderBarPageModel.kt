package uk.co.grahamcox.worlds.e2e.browser

import org.assertj.core.api.Assertions
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory

/**
 * Page Model representing the header bar
 */
class HeaderBarPageModel(element: WebElement) {
    init {
        PageFactory.initElements(DefaultElementLocatorFactory(element), this)
    }

    /** The Web Element that represents the Login menu link */
    @FindBy(css = "[data-test=loginMenu]")
    private lateinit var loginMenuElement: WebElement

    /** The Web Element that represents the Login menu link */
    @FindBy(css = "[data-test=loggedInMenu]")
    private lateinit var loggedInMenuElement: WebElement

    /**
     * Check if we're logged in or not
     */
    val loggedIn: Boolean
        get() {
            return !loginMenuElement.safeIsVisible
        }

    /**
     * The page model for the logged in user menu
     */
    val loggedInMenu: LoggedInMenuPageModel
        get() {
            return LoggedInMenuPageModel(loggedInMenuElement)
        }

    /**
     * Click the link to start logging in
     */
    fun clickLogin() {
        Assertions.assertThat(loggedIn)
                .`as`("The user is already logged in")
                .isFalse()
        loginMenuElement.click()
    }
}
