package uk.co.grahamcox.worlds.e2e.authentication

import cucumber.api.java8.En
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
            if (!header.loginMenuVisible) {
                throw AssertionError("Login menu is not visible")
            }
        }
    }
}
