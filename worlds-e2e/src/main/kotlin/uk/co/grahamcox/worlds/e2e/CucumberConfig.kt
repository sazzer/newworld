package uk.co.grahamcox.worlds.e2e

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import uk.co.grahamcox.worlds.e2e.selenium.SeleniumConfig

/**
 * The core configuration for the Cucumber tests
 */
@Configuration
@Import(
        SeleniumConfig::class
)
class CucumberConfig
