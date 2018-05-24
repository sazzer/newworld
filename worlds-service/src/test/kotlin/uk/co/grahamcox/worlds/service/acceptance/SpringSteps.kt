package uk.co.grahamcox.worlds.service.acceptance

import cucumber.api.java.Before
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import uk.co.grahamcox.worlds.service.WorldsServiceApplication

/**
 * Cucumber steps class for ensuring that Spring is started
 */
@SpringBootTest(classes = [WorldsServiceApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
