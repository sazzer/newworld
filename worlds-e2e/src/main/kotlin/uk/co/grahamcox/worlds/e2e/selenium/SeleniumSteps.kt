package uk.co.grahamcox.worlds.e2e.selenium

import cucumber.api.java8.En

/**
 * Cucumber steps for interacting directly with Selenium
 */
class SeleniumSteps(
        private val webBrowser: WebBrowser
) : En {

    init {
        When("I open the main page") {
            webBrowser.openHomePage()
        }

        After { scenario ->
            webBrowser.quit()
        }
    }
}
