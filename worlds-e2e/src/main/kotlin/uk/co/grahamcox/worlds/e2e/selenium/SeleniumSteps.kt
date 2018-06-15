package uk.co.grahamcox.worlds.e2e.selenium

import cucumber.api.java8.En
import org.openqa.selenium.WebDriver
import org.springframework.beans.factory.annotation.Value

/**
 * Cucumber steps for interacting directly with Selenium
 */
class SeleniumSteps(
        private val webDriver: WebDriver,
        @Value("\${selenium.url}") private val baseUrl: String
) : En {

    init {
        When("I open the main page") {
            webDriver.get(baseUrl)
        }

        After { scenario ->
            webDriver.quit()
        }
    }
}
