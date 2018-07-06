package uk.co.grahamcox.worlds.e2e.browser

import org.openqa.selenium.WebDriver
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.annotation.Value
import kotlin.reflect.KFunction1

/**
 * The Web Browser interface to use, based off of a WebDriver
 */
class WebDriverBrowser(
        val webDriver: WebDriver,
        @Value("\${webapp.url}") private val baseUrl: String
) : DisposableBean, WebBrowser {
    /**
     * Open the application home
     */
    override fun openHomePage() {
        webDriver.get(baseUrl)
    }

    /**
     * Quit the web driver
     */
    override fun destroy() {
        webDriver.quit()
    }

    /**
     * Build the required page model for the page that the web driver is currently looking at
     */
    override fun <T> getPage(constructor: KFunction1<WebDriver, T>) = constructor(webDriver)

    /**
     * Build the required page model for the page that the web driver is currently looking at
     */
    override fun <T> getPage(pageName: String, constructor: KFunction1<WebDriver, T>): T {
        switchWindows(pageName)
        return getPage(constructor)
    }

    /**
     * Get the window handle of the current window
     */
    val currentWindowHandle: String
        get() {
            webDriver.currentUrl // Ensure that there is a current window
            return webDriver.windowHandle
        }

    /**
     * Switch windows to the one with the given name
     */
    fun switchWindows(pageName: String) {
        webDriver.switchTo().window(pageName)
    }
}
