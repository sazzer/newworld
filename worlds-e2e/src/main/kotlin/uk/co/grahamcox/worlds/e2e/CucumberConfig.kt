package uk.co.grahamcox.worlds.e2e

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import uk.co.grahamcox.worlds.cucumber.database.DatabaseCleaner
import uk.co.grahamcox.worlds.cucumber.users.UsersSeederConfig
import uk.co.grahamcox.worlds.e2e.selenium.SeleniumConfig
import java.time.Clock

/**
 * The core configuration for the Cucumber tests
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@Configuration
@Import(
        SeleniumConfig::class,
        UsersSeederConfig::class
)
class CucumberConfig(context: GenericApplicationContext) {
    init {
        beans {
            bean<Clock> {
                Clock.systemUTC()
            }
            bean<DatabaseCleaner>()
        }.initialize(context)
    }
}
