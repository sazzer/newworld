package uk.co.grahamcox.worlds.e2e.browser

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.annotation.Value

/**
 * The Web Browser interface to use
 */
class WebBrowser(
        private val webDriver: WebDriver,
        @Value("\${webapp.url}") private val baseUrl: String
) : DisposableBean {
    /** The representation of the main application page */
    val applicationPage: ApplicationPageModel
        get() {
            val appElement = webDriver.waitUntilExists(By.id("App"))
            return ApplicationPageModel(appElement)
        }

    /**
     * Open the application home
     */
    fun openHomePage() {
        webDriver.get(baseUrl)
    }

    /**
     * Quit the web driver
     */
    override fun destroy() {
        webDriver.quit()
    }
}
