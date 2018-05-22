package uk.co.grahamcox.worlds.service.acceptance

import cucumber.api.java8.En
import org.junit.jupiter.api.Assertions
import org.springframework.http.HttpStatus
import uk.co.grahamcox.worlds.service.acceptance.requester.Requester

/**
 * Cucumber steps for checking the health of the system
 */
class HealthcheckSteps(val requester: Requester) : En {

    init {
        When("^I get the health of the system$") {
            requester.get("/actuator/health")
        }

        Then("^the system is healthy$") {
            Assertions.assertEquals(HttpStatus.OK, requester.lastResponse.statusCode)
        }
    }
}
