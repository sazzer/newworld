package uk.co.grahamcox.worlds.service.acceptance.users

import cucumber.api.java8.En
import io.cucumber.datatable.DataTable
import uk.co.grahamcox.worlds.service.acceptance.database.DatabaseExistsVerifier
import uk.co.grahamcox.worlds.cucumber.database.DatabaseSeeder
import uk.co.grahamcox.worlds.service.acceptance.requester.RequestSubmitter
import uk.co.grahamcox.worlds.service.acceptance.requester.ResponseMatcher

/**
 * Cucumber steps for working with Users
 */
class UserSteps(
        private val userRetrievalRequestSubmitter: RequestSubmitter,
        private val userUpdateRequestSubmitter: RequestSubmitter,
        private val changePasswordRequestSubmitter: RequestSubmitter,
        private val userSeeder: DatabaseSeeder,
        private val userResponseMatcher: ResponseMatcher,
        private val userEmailExistsVerifier: DatabaseExistsVerifier
) : En {

    init {
        Given("^a user exists with details:$") { details: DataTable ->
            userSeeder.seed(details.asMap(String::class.java, String::class.java))
        }

        When("""^I get the user with ID "(.+)"$""") { id: String ->
            userRetrievalRequestSubmitter.makeRequest(mapOf("ID" to id))
        }

        When("""^I update the user with ID "(.+)" to have details:$""") { id: String, details: DataTable ->
            val payload = details.asMap<String, String>(String::class.java, String::class.java) +
                    mapOf("ID" to id)

            userUpdateRequestSubmitter.makeRequest(payload)
        }

        When("""^I change the password of the user with ID "(.+)" from "(.+)" to "(.+)"$""") {
            id: String, old: String, new: String ->
            val payload = mapOf(
                    "ID" to id,
                    "Old" to old,
                    "New" to new)

            changePasswordRequestSubmitter.makeRequest(payload)
        }

        Then("^I get a user with details:$") { details: DataTable ->
            userResponseMatcher.match(details.asMap(String::class.java, String::class.java))
        }

        Then("""^no user now exists with email address "(.+)"$""") { email: String ->
            userEmailExistsVerifier.checkNotExists(email)
        }

        Then("""^a user now exists with email address "(.+)"$""") { email: String ->
            userEmailExistsVerifier.checkExists(email)
        }
    }
}
