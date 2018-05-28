package uk.co.grahamcox.worlds.service.acceptance.openid

import cucumber.api.java8.En
import io.cucumber.datatable.DataTable
import org.springframework.http.HttpMethod
import org.springframework.web.util.UriComponentsBuilder
import uk.co.grahamcox.worlds.service.acceptance.requester.HTMLMatcher
import uk.co.grahamcox.worlds.service.acceptance.requester.Requester

/**
 * Cucumber steps for OpenID Authorization
 */
class AuthorizeSteps(
        private val requester: Requester,
        private val openidAuthorizeErrorMatcher: HTMLMatcher,
        private val openidEmailEntryScreenMatcher: HTMLMatcher) : En {
    init {
        When("I start OpenID Authorization with parameters:$") { parameters: DataTable ->
            val uri = UriComponentsBuilder.fromPath("/openid/authorize")

            parameters.asMap<String, String>(String::class.java, String::class.java)
                    .forEach {
                        uri.queryParam(it.key, it.value)
                    }

            requester.makeRequest(uri.toUriString(), HttpMethod.GET, null, String::class.java)
        }

        Then("""^I get an OpenID Authorization error message of "(.+)"$""") { error: String ->
            openidAuthorizeErrorMatcher.match(mapOf(
                    "Error Message" to error
            ))
        }

        Then("""^I get an OpenID Email entry screen with details:$""") { details: DataTable ->
            openidEmailEntryScreenMatcher.match(details.asMap(String::class.java, String::class.java))
        }
    }
}
