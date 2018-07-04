package uk.co.grahamcox.worlds.e2e.browser

import org.openqa.selenium.WebDriver
import org.springframework.beans.factory.annotation.Value

/**
 * The Web Browser interface to use
 */
class WebBrowser(
        private val webDriver: WebDriver,
        @Value("\${selenium.url}") private val baseUrl: String
) {
    /**
     * Open the application home
     */
    fun openHomePage() {
        webDriver.get(baseUrl)
    }

    /**
     * Quit the web driver
     */
    fun quit() {
        webDriver.quit()
    }
}
