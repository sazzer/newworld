package uk.co.grahamcox.worlds.service.acceptance.requester

import cucumber.api.java8.En
import org.junit.jupiter.api.Assertions
import org.springframework.http.HttpStatus

/**
 * Cucumber steps for working directly with the requester
 */
class RequesterSteps(
        private val requester: Requester
) : En {
    init {
        Before { scenario -> requester.reset() }

        Then("^I get an? (.+) response$") { statusName: String ->
            val convertedName = statusName.toUpperCase().replace(" ", "_")
            val httpStatus = HttpStatus.values().find { it.name == convertedName }

            Assertions.assertNotNull(httpStatus, "Unknown Status: $statusName")
            Assertions.assertEquals(httpStatus, requester.lastResponse.statusCode)
        }
    }
}
