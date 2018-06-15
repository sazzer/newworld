package uk.co.grahamcox.worlds.e2e.browser

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.springframework.beans.factory.annotation.Value

/**
 * The Web Browser interface to use
 */
class WebBrowser(
        private val webDriver: WebDriver,
        @Value("\${selenium.url}") private val baseUrl: String
) {
    /** The representation of the main application page */
    val applicationPage: ApplicationPage
    get() {
        val appElement = webDriver.waitUntilExists(By.id("App"))
        return ApplicationPage(appElement)
    }

    /**
     * Open the application home
     */
    fun openHomePage() {
        webDriver.get(baseUrl)

        webDriver.waitUntilExists(By.id("App"))
    }

    /**
     * Quit the web driver
     */
    fun quit() {
        webDriver.quit()
    }
}
