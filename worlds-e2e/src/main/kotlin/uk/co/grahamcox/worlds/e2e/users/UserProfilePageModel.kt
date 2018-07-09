package uk.co.grahamcox.worlds.e2e.users

import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory

/**
 * Representation of the user profile page
 */
class UserProfilePageModel(element: SearchContext) {
    init {
        PageFactory.initElements(DefaultElementLocatorFactory(element), this)
    }

    /** The user profile form */
    @FindBy(css = "form[data-test=userProfileForm]")
    private lateinit var userProfileFormElement: WebElement

    /** The user profile form */
    val userProfileForm: UserProfileFormPageModel
        get() {
            return UserProfileFormPageModel(userProfileFormElement)
        }

}
