package uk.co.grahamcox.worlds.e2e.browser

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory

/**
 * Representation of the header bar
 */
class HeaderBarPageModel(parent: WebElement) {
    init {
        PageFactory.initElements(DefaultElementLocatorFactory(parent), this)
    }

    /** The Web Element that represents the Login Menu Entry */
    @FindBy(css = "[data-test=loginMenu2]")
    private lateinit var loginMenuElement: WebElement

    val loginMenuVisible: Boolean
    get() {
        return loginMenuElement.safeIsVisible
    }
}
