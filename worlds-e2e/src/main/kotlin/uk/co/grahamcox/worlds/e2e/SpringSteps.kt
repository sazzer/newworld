package uk.co.grahamcox.worlds.e2e

import cucumber.api.java.Before
import org.springframework.boot.test.context.SpringBootTest

/**
 * Cucumber steps class for ensuring that Spring is started
 */
@SpringBootTest(classes = [CucumberConfig::class])
class SpringSteps {
    /**
     * No-op function so that there is a Cucumber annotated method in this class
     */
    @Before
    fun startSpring() {
    }
}
