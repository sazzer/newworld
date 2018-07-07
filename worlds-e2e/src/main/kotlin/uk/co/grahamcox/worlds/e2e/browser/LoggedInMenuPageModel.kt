package uk.co.grahamcox.worlds.e2e.browser

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory

/**
 * Page model representing the menu for when the user is logged in
 */
class LoggedInMenuPageModel(element: WebElement) {
    init {
        PageFactory.initElements(DefaultElementLocatorFactory(element), this)
    }

    /** The Web Element that represents the title of the menu link */
    @FindBy(css = "div.text")
    private lateinit var usernameElement: WebElement

    /**
     * The name of the user that is logged in
     */
    val username: String
        get() {
            return usernameElement.text
        }
}
