package uk.co.grahamcox.worlds.e2e.browser

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory

/**
 * Representation of the header bar
 */
class HeaderBarPageModel(parent: WebElement) {
    init {
        PageFactory.initElements(DefaultElementLocatorFactory(parent), this)
    }

}
