package uk.co.grahamcox.worlds.e2e.selenium

import cucumber.api.java8.En
import uk.co.grahamcox.worlds.e2e.browser.WebBrowser

/**
 * Cucumber steps for interacting directly with Selenium
 */
class SeleniumSteps(
        private val webBrowser: WebBrowser
) : En {

    init {
        When("I open the home page") {
            webBrowser.openHomePage()
        }

        After { _ ->
            webBrowser.quit()
        }
    }
}
