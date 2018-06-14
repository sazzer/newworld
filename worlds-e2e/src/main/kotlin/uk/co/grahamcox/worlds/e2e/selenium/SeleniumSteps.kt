package uk.co.grahamcox.worlds.e2e.selenium

import cucumber.api.java8.En
import org.openqa.selenium.WebDriver
import org.springframework.beans.factory.annotation.Autowired

/**
 * Cucumber steps for interacting directly with Selenium
 */
class SeleniumSteps : En {
    /** The WebDriver Factory */
    @Autowired
    private lateinit var webDriver: WebDriver

    init {
        When("I open the main page") {
            webDriver.get("http://www.google.com")
        }

        After { scenario ->
            webDriver.quit()
        }
    }
}
