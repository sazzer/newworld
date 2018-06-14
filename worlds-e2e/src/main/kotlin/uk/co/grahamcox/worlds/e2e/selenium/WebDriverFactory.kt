package uk.co.grahamcox.worlds.e2e.selenium

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.beans.factory.config.AbstractFactoryBean

/**
 * Mechanism to get a WebDriver to use for tests
 */
class WebDriverFactory : AbstractFactoryBean<WebDriver>() {
    /** Get the type of object we're creating */
    override fun getObjectType() = WebDriver::class.java

    /**
     * Create the webdriver to use
     */
    override fun createInstance(): WebDriver {
        return ChromeDriver()
    }

}
