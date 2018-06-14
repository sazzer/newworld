package uk.co.grahamcox.worlds.e2e

import cucumber.api.java.Before
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

/**
 * Cucumber steps class for ensuring that Spring is started
 */
@ContextConfiguration(classes = [CucumberConfig::class])
@ActiveProfiles("test")
class SpringSteps {
    /**
     * No-op function so that there is a Cucumber annotated method in this class
     */
    @Before
    fun startSpring() {
    }
}
