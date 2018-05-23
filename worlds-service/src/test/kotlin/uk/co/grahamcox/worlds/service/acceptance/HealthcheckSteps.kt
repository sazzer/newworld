package uk.co.grahamcox.worlds.service.acceptance

import cucumber.api.java8.En
import uk.co.grahamcox.worlds.service.acceptance.requester.Requester

/**
 * Cucumber steps for checking the health of the system
 */
class HealthcheckSteps(private val requester: Requester) : En {

    init {
        When("^I check the health of the system$") {
            requester.get("/actuator/health")
        }
    }
}
