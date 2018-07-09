package uk.co.grahamcox.worlds.e2e.users

import cucumber.api.java8.En
import io.cucumber.datatable.DataTable
import uk.co.grahamcox.worlds.e2e.browser.ApplicationPageModel
import uk.co.grahamcox.worlds.e2e.browser.WebDriverBrowser
import uk.co.grahamcox.worlds.e2e.form.FormWrapper

/**
 * Cucumber steps
 */
class UserProfileSteps(
        private val webBrowser: WebDriverBrowser,
        private val userProfileFormWrapper: FormWrapper<UserProfileFormPageModel>
) : En {
    init {
        When("^I view the users profile$") {
            val applicationPageModel = webBrowser.getPage(::ApplicationPageModel)
            applicationPageModel.header.loggedInMenu.viewProfile()
        }

        Then("^I update the users profile to:$") { details: DataTable ->
            val userProfilePage = webBrowser.getPage(::UserProfilePageModel)
            val userProfileForm = userProfilePage.userProfileForm

            userProfileFormWrapper.update(userProfileForm,
                    details.asMap(String::class.java, String::class.java))
            userProfileForm.save()

        }

        Then("^the users profile has details:$") { details: DataTable ->
            val userProfilePage = webBrowser.getPage(::UserProfilePageModel)
            userProfileFormWrapper.match(userProfilePage.userProfileForm,
                    details.asMap(String::class.java, String::class.java))
        }
    }
}
