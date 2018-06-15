package uk.co.grahamcox.worlds.e2e.browser

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory

/**
 * Representation of the current application page
 */
class ApplicationPage(element: WebElement) {

    init {
        PageFactory.initElements(DefaultElementLocatorFactory(element), this)
    }

    /** The Web Element that represents the Header Bar */
    @FindBy(css = "[data-test=headerBar]")
    private lateinit var headerBarElement: WebElement

    /** Get the header bar of the page */
    val header: HeaderBarPageModel
    get() {
        if (!headerBarElement.isDisplayed) {
            throw AssertionError("Header Bar is not visible")
        }
        return HeaderBarPageModel(headerBarElement)
    }
}
