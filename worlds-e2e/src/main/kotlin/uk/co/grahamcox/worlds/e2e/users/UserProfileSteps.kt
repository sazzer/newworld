package uk.co.grahamcox.worlds.e2e.users

import cucumber.api.java8.En
import io.cucumber.datatable.DataTable
import org.assertj.core.api.Assertions
import org.awaitility.Awaitility
import uk.co.grahamcox.worlds.e2e.browser.ApplicationPageModel
import uk.co.grahamcox.worlds.e2e.browser.WebDriverBrowser
import uk.co.grahamcox.worlds.e2e.form.FormWrapper
import java.util.concurrent.TimeUnit

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

            val userProfilePage = webBrowser.getPage(::UserProfilePageModel)
            val userProfileForm = userProfilePage.viewUserProfileForm()

            Awaitility.await()
                    .atMost(5, TimeUnit.SECONDS)
                    .until { userProfileForm.visible }
        }

        Then("^I update the users profile to:$") { details: DataTable ->
            val userProfilePage = webBrowser.getPage(::UserProfilePageModel)
            val userProfileForm = userProfilePage.viewUserProfileForm()

            userProfileFormWrapper.update(userProfileForm,
                    details.asMap(String::class.java, String::class.java))
            userProfileForm.save()

            Awaitility.await()
                    .atMost(5, TimeUnit.SECONDS)
                    .until { !userProfileForm.loading }
        }

        Then("^the users profile has details:$") { details: DataTable ->
            val userProfilePage = webBrowser.getPage(::UserProfilePageModel)
            userProfileFormWrapper.match(userProfilePage.viewUserProfileForm(),
                    details.asMap(String::class.java, String::class.java))
        }

        Then("""^updating the users profile failed with "(.+)"$""") { error: String ->
            val userProfilePage = webBrowser.getPage(::UserProfilePageModel)
            val userProfileForm = userProfilePage.viewUserProfileForm()

            Assertions.assertThat(userProfileForm.errorVisible)
                    .`as`("No error message was visible")
                    .isTrue()
            Assertions.assertThat(userProfileForm.errorText)
                    .isEqualTo(error)
        }
    }
}
