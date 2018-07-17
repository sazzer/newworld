package uk.co.grahamcox.worlds.service.acceptance.worlds

import cucumber.api.java8.En
import io.cucumber.datatable.DataTable
import uk.co.grahamcox.worlds.cucumber.database.DatabaseSeeder
import uk.co.grahamcox.worlds.service.acceptance.requester.RequestSubmitter
import uk.co.grahamcox.worlds.service.acceptance.requester.ResponseMatcher

/**
 * Cucumber steps for working with Worlds
 */
class WorldsSteps(
        private val worldRetrievalRequestSubmitter: RequestSubmitter,
        private val worldSeeder: DatabaseSeeder,
        private val worldResponseMatcher: ResponseMatcher
) : En {

    init {
        Given("^a world exists with details:$") { details: DataTable ->
            worldSeeder.seed(details.asMap(String::class.java, String::class.java))
        }

        When("""^I get the world with ID "(.+)"$""") { id: String ->
            worldRetrievalRequestSubmitter.makeRequest(mapOf("ID" to id))
        }

        Then("^I get a world with details:$") { details: DataTable ->
            worldResponseMatcher.match(details.asMap(String::class.java, String::class.java))
        }
    }
}
