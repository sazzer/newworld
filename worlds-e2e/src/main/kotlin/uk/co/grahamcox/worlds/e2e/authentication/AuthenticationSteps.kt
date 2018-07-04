package uk.co.grahamcox.worlds.e2e.authentication

import cucumber.api.java8.En
import org.assertj.core.api.Assertions
import uk.co.grahamcox.worlds.e2e.browser.WebBrowser

/**
 * Cucumber steps for dealing with authentication
 */
class AuthenticationSteps(
        private val webBrowser: WebBrowser
) : En {

    init {
        Then("^I am not logged in$") {
            Assertions.assertThat(webBrowser.applicationPage.header.loggedIn)
                    .`as`("The user is logged in")
                    .isFalse()

        }
    }
}
