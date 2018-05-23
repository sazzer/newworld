package uk.co.grahamcox.worlds.service.acceptance.requester

import cucumber.api.java8.En
import io.cucumber.datatable.DataTable

/**
 * Cucumber steps for asserting a Problem response
 */
class ProblemSteps(
        private val problemResponseMatcher: ResponseMatcher
) : En {
    init {
        Then("^I get a problem response of:$") { details: DataTable ->
            problemResponseMatcher.match(details.asMap(String::class.java, String::class.java))
        }
    }
}
