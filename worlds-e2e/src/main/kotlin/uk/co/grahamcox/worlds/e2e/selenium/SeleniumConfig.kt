package uk.co.grahamcox.worlds.e2e.selenium

import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans

/**
 * Configure the Selenium system
 */
@Configuration
class SeleniumConfig(context: GenericApplicationContext) {
    init {
        beans {
            bean<WebDriverFactory>()
        }.initialize(context)
    }
}
