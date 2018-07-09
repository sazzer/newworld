package uk.co.grahamcox.worlds.e2e.users

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory
import uk.co.grahamcox.worlds.e2e.selenium.FormFieldDelegate

/**
 * Representation of the actual user profile form
 */
class UserProfileFormPageModel(element: WebElement) {
    init {
        PageFactory.initElements(DefaultElementLocatorFactory(element), this)
    }
    /** The email address */
    var email: String by FormFieldDelegate(element, By.name("email"))

    /** The username */
    var username: String by FormFieldDelegate(element, By.name("username"))

    /** The display name */
    var displayName: String by FormFieldDelegate(element, By.name("displayName"))

    /** The submit button */
    @FindBy(css = "button.primary")
    private lateinit var submit: WebElement
}
