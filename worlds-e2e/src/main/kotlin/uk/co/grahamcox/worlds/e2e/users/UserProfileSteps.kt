package uk.co.grahamcox.worlds.e2e.users

import cucumber.api.java8.En
import io.cucumber.datatable.DataTable
import uk.co.grahamcox.worlds.e2e.browser.ApplicationPageModel
import uk.co.grahamcox.worlds.e2e.browser.WebDriverBrowser
import uk.co.grahamcox.worlds.e2e.matcher.FormField
import uk.co.grahamcox.worlds.e2e.matcher.FormMatcher

/**
 * Cucumber steps
 */
class UserProfileSteps(
        private val webBrowser: WebDriverBrowser,
        private val userProfileFormMatcher: FormMatcher<UserProfileFormPageModel>
) : En {
    init {
        When("^I view the users profile$") {
            val applicationPageModel = webBrowser.getPage(::ApplicationPageModel)
            applicationPageModel.header.loggedInMenu.viewProfile()
        }

        Then("^the users profile has details:$") { details: DataTable ->
            val userProfilePage = webBrowser.getPage(::UserProfilePageModel)
            userProfileFormMatcher.match(userProfilePage.userProfileForm,
                    details.asMap(String::class.java, String::class.java))
        }
    }
}
