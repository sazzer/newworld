package uk.co.grahamcox.worlds.service.acceptance.users

import cucumber.api.java8.En
import uk.co.grahamcox.worlds.service.acceptance.requester.Requester

/**
 * Cucumber steps for working with Users
 */
class UserSteps(private val requester: Requester) : En {

    init {
        When("""^I get the user with ID "(.+)"$""") { id: String ->
            requester.get("/api/users/$id")
        }
    }
}
