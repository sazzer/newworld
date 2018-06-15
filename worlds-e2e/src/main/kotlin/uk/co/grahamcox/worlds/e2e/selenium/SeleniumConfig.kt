package uk.co.grahamcox.worlds.e2e.selenium

import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import uk.co.grahamcox.worlds.e2e.browser.WebBrowser

/**
 * Configure the Selenium system
 */
@Configuration
class SeleniumConfig(context: GenericApplicationContext) {
    init {
        beans {
            bean<WebDriverFactory>()
            bean<WebBrowser>()
        }.initialize(context)
    }
}
