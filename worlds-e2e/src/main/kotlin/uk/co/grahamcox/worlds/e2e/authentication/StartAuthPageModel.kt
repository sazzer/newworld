package uk.co.grahamcox.worlds.e2e.authentication

import org.openqa.selenium.NotFoundException
import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory

/**
 * Page Model to represent the screen for starting authentication
 */
class StartAuthPageModel(base: SearchContext) {
    init {
        PageFactory.initElements(DefaultElementLocatorFactory(base), this)

        try {
            form.isDisplayed
        } catch (e: NotFoundException) {
            throw IllegalStateException("Start Auth Form not present on page", e)
        }
    }

    /** The Web Element that represents the actual form */
    @FindBy(css = "form[data-test=startAuth]")
    private lateinit var form: WebElement

    /**
     * Get the form for starting authentication
     */
    val startAuthForm: StartAuthForm
    get() {
        return StartAuthForm(form)
    }
}
