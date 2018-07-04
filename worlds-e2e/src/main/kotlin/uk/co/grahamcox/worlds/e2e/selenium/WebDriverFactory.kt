package uk.co.grahamcox.worlds.e2e.selenium

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.AbstractFactoryBean
import java.net.URL

/**
 * Mechanism to get a WebDriver to use for tests
 */
class WebDriverFactory(
        @Value("\${selenium.url:}") private val seleniumUrl: String
) : AbstractFactoryBean<WebDriver>() {
    /** Get the type of object we're creating */
    override fun getObjectType() = WebDriver::class.java

    /**
     * Create the webdriver to use
     */
    override fun createInstance(): WebDriver {
        return when (seleniumUrl) {
            "" -> ChromeDriver()
            else -> RemoteWebDriver(URL(seleniumUrl), DesiredCapabilities.chrome())
        }
    }

}
