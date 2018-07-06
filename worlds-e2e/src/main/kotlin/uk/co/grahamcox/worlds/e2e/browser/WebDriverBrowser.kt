package uk.co.grahamcox.worlds.e2e.browser

import org.openqa.selenium.WebDriver
import org.slf4j.LoggerFactory
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
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(WebDriverBrowser::class.java)
    }

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
        LOG.debug("All window handles: {}", webDriver.windowHandles)
        LOG.debug("Current window handle: {}", webDriver.windowHandle)
        switchWindows(pageName)
        LOG.debug("New window handle: {}", webDriver.windowHandle)
        LOG.debug("Window URL: {}", webDriver.currentUrl)
        return getPage(constructor)
    }

    /**
     * Get the window handle of the current window
     */
    val currentWindowHandle: String
        get() {
            LOG.debug("Current window handle: {}", webDriver.windowHandle)
            LOG.debug("Window URL: {}", webDriver.currentUrl)
            return webDriver.windowHandle
        }

    /**
     * Switch windows to the one with the given name
     */
    fun switchWindows(pageName: String) {
        webDriver.switchTo().window(pageName)
    }
}
