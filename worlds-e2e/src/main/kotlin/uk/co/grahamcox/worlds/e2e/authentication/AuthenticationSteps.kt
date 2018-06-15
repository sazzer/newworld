package uk.co.grahamcox.worlds.e2e.authentication

import cucumber.api.java8.En
import org.assertj.core.api.Assertions
import uk.co.grahamcox.worlds.e2e.browser.WebBrowser

/**
 * Cucumber steps for dealing with Authentication
 */
class AuthenticationSteps(
        private val webBrowser: WebBrowser
) : En {

    init {
        Then("I am not logged in") {
            val header = webBrowser.applicationPage.header
            Assertions.assertThat(header.loginMenuVisible)
                    .`as`("Check visibility of the Login Menu Link")
                    .isTrue()
        }
    }
}
