package uk.co.grahamcox.worlds.e2e.users

import cucumber.api.java8.En
import io.cucumber.datatable.DataTable
import uk.co.grahamcox.worlds.cucumber.database.DatabaseSeeder

/**
 * Cucumber steps for working directly with user details
 */
class UserSteps(
        private val userSeeder: DatabaseSeeder
) : En {
    init {
        Given("^a user exists with details:$") { details: DataTable ->
            userSeeder.seed(details.asMap(String::class.java, String::class.java))
        }

    }
}
