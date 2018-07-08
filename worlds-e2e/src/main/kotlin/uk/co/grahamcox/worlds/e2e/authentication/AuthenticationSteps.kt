package uk.co.grahamcox.worlds.e2e.authentication

import cucumber.api.java8.En
import io.cucumber.datatable.DataTable
import org.assertj.core.api.Assertions
import org.awaitility.Awaitility
import org.openqa.selenium.NoSuchWindowException
import org.slf4j.LoggerFactory
import uk.co.grahamcox.worlds.e2e.browser.ApplicationPageModel
import uk.co.grahamcox.worlds.e2e.browser.WebDriverBrowser
import java.util.concurrent.TimeUnit

/**
 * Cucumber steps for dealing with authentication
 */
class AuthenticationSteps(
        private val webBrowser: WebDriverBrowser
) : En {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(AuthenticationSteps::class.java)
    }

    init {
        When("^I register a user with details:$") { dataTable: DataTable ->
            val values = dataTable.asMap<String, String>(String::class.java, String::class.java)

            val startingWindowHandle = webBrowser.currentWindowHandle

            val applicationPageModel = webBrowser.getPage(::ApplicationPageModel)
            applicationPageModel.header.clickLogin()

            val startAuthPageModel = webBrowser.getPage("worldsAuth", ::StartAuthPageModel)
            startAuthPageModel.startAuthForm.populateAndSubmit(values)

            val registerPageModel = webBrowser.getPage(::RegisterPageModel)
            registerPageModel.registerUserForm.populateAndSubmit(values)

            // This nasty hackery ensures that the current window has closed, and then switches the focus back to the
            // main one
            Awaitility.await().atMost(5, TimeUnit.SECONDS)
                    .until {
                        try {
                            val currentWindow = webBrowser.currentWindowHandle
                            LOG.info("Current window: {}, Starting window: {}", currentWindow, startingWindowHandle)
                            webBrowser.currentWindowHandle == startingWindowHandle
                        } catch (e: NoSuchWindowException) {
                            // This is expected
                            LOG.info("There is no current window")
                            true
                        }
                    }
            webBrowser.switchWindows("")
        }

        When("^I log in with details:$") { dataTable: DataTable ->
            val values = dataTable.asMap<String, String>(String::class.java, String::class.java)

            val startingWindowHandle = webBrowser.currentWindowHandle

            val applicationPageModel = webBrowser.getPage(::ApplicationPageModel)
            applicationPageModel.header.clickLogin()

            val startAuthPageModel = webBrowser.getPage("worldsAuth", ::StartAuthPageModel)
            startAuthPageModel.startAuthForm.populateAndSubmit(values)

            val registerPageModel = webBrowser.getPage(::LoginPageModel)
            registerPageModel.loginUserForm.populateAndSubmit(values)

            // This nasty hackery ensures that the current window has closed, and then switches the focus back to the
            // main one
            Awaitility.await().atMost(5, TimeUnit.SECONDS)
                    .until {
                        try {
                            val currentWindow = webBrowser.currentWindowHandle
                            LOG.info("Current window: {}, Starting window: {}", currentWindow, startingWindowHandle)
                            webBrowser.currentWindowHandle == startingWindowHandle
                        } catch (e: NoSuchWindowException) {
                            // This is expected
                            LOG.info("There is no current window")
                            true
                        }
                    }
            webBrowser.switchWindows("")
        }

        Then("^I am not logged in$") {
            val applicationPageModel = webBrowser.getPage(::ApplicationPageModel)
            Assertions.assertThat(applicationPageModel.header.loggedIn)
                    .`as`("The user is logged in")
                    .isFalse()
        }

        Then("""^I am logged in as "(.+)"$""") { user: String ->
            val applicationPageModel = webBrowser.getPage(::ApplicationPageModel)
            Assertions.assertThat(applicationPageModel.header.loggedIn)
                    .`as`("The user is logged in")
                    .isTrue()

            Assertions.assertThat(applicationPageModel.header.loggedInMenu.username)
                    .isEqualTo(user)
        }
    }
}
