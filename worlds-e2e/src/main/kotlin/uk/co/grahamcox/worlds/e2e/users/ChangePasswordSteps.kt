package uk.co.grahamcox.worlds.e2e.users

import cucumber.api.java8.En
import io.cucumber.datatable.DataTable
import org.assertj.core.api.Assertions
import org.awaitility.Awaitility
import uk.co.grahamcox.worlds.e2e.browser.WebDriverBrowser
import uk.co.grahamcox.worlds.e2e.form.FormWrapper
import java.util.concurrent.TimeUnit


/**
 * Cucumber steps
 */
class ChangePasswordSteps(
        private val webBrowser: WebDriverBrowser,
        private val changePasswordFormWrapper: FormWrapper<ChangePasswordFormPageModel>
) : En {
    init {
        When("^I update the users password:$") { details: DataTable ->
            val userProfilePage = webBrowser.getPage(::UserProfilePageModel)
            val changePasswordForm = userProfilePage.viewChangePasswordForm()

            changePasswordFormWrapper.update(changePasswordForm,
                    details.asMap(String::class.java, String::class.java))
            changePasswordForm.save()

            Awaitility.await()
                    .atMost(5, TimeUnit.SECONDS)
                    .until { !changePasswordForm.loading }
        }

        Then("^the password is updated successfully$") {
            val userProfilePage = webBrowser.getPage(::UserProfilePageModel)
            val changePasswordForm = userProfilePage.viewChangePasswordForm()

            Assertions.assertThat(changePasswordForm.successVisible)
                    .isTrue()
        }

        Then("""^updating the users password failed with "(.+)"$""") { error: String ->
            val userProfilePage = webBrowser.getPage(::UserProfilePageModel)
            val changePasswordForm = userProfilePage.viewChangePasswordForm()

            Assertions.assertThat(changePasswordForm.errorVisible)
                    .`as`("No error message was visible")
                    .isTrue()
            Assertions.assertThat(changePasswordForm.errorText)
                    .isEqualTo(error)
        }

    }
}
