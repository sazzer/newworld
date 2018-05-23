package uk.co.grahamcox.worlds.service.acceptance.users

import cucumber.api.java8.En
import io.cucumber.datatable.DataTable
import uk.co.grahamcox.worlds.service.acceptance.database.DatabaseSeeder
import uk.co.grahamcox.worlds.service.acceptance.requester.Requester

/**
 * Cucumber steps for working with Users
 */
class UserSteps(
        private val requester: Requester,
        private val userSeeder: DatabaseSeeder) : En {

    init {
        Given("^a user exists with details:$") { details: DataTable ->
            userSeeder.seed(details.asMap(String::class.java, String::class.java))
        }

        When("""^I get the user with ID "(.+)"$""") { id: String ->
            requester.get("/api/users/$id")
        }
    }
}
