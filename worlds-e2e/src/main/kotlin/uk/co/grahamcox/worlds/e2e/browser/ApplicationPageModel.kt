package uk.co.grahamcox.worlds.e2e.browser

import org.assertj.core.api.Assertions
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory

/**
 * Page model representing the entire application
 */
class ApplicationPageModel(element: WebElement) {
    init {
        PageFactory.initElements(DefaultElementLocatorFactory(element), this)
    }

    /** The Web Element that represents the Header Bar */
    @FindBy(css = "[data-test=headerBar]")
    private lateinit var headerBarElement: WebElement

    /** Get the header bar of the page */
    val header: HeaderBarPageModel
        get() {
            Assertions.assertThat(headerBarElement.isDisplayed)
                    .`as`("The header bar should be visible")
                    .isTrue()
            return HeaderBarPageModel(headerBarElement)
        }

}
