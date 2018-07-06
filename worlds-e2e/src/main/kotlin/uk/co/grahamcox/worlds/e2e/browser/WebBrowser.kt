package uk.co.grahamcox.worlds.e2e.browser

import org.openqa.selenium.WebDriver
import kotlin.reflect.KFunction1

/**
 * Interface describing some common interactions with the web browser
 */
interface WebBrowser {
    /**
     * Open the application home
     */
    fun openHomePage()

    /**
     * Build the required page model for the page that the web driver is currently looking at
     */
    fun <T> getPage(constructor: KFunction1<WebDriver, T>): T

    /**
     * Build the required page model for the page that the web driver is currently looking at
     */
    fun <T> getPage(pageName: String, constructor: KFunction1<WebDriver, T>): T
}
