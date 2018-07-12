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

    /** The user profile menu */
    @FindBy(css = "[data-test=userProfileMenuProfile]")
    private lateinit var userProfileFormMenu: WebElement

    /** The user profile form */
    @FindBy(css = "form[data-test=userProfileForm]")
    private lateinit var userProfileFormElement: WebElement

    /** The change password menu */
    @FindBy(css = "[data-test=userProfileMenuPassword]")
    private lateinit var changePasswordMenu: WebElement

    /** The change password form */
    @FindBy(css = "form[data-test=changePasswordForm]")
    private lateinit var changePasswordFormElement: WebElement

    /**
     * View the user profile form
     */
    fun viewUserProfileForm(): UserProfileFormPageModel {
        userProfileFormMenu.click()
        return UserProfileFormPageModel(userProfileFormElement)
    }

    /**
     * View the Change Password form
     */
    fun viewChangePasswordForm(): ChangePasswordFormPageModel {
        changePasswordMenu.click()
        return ChangePasswordFormPageModel(changePasswordFormElement)
    }
}
