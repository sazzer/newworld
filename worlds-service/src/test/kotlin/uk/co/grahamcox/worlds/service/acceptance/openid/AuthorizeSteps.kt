package uk.co.grahamcox.worlds.service.acceptance.openid

import cucumber.api.java8.En
import io.cucumber.datatable.DataTable
import org.springframework.http.HttpMethod
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.util.UriComponentsBuilder
import uk.co.grahamcox.worlds.service.acceptance.requester.Requester
import uk.co.grahamcox.worlds.service.acceptance.requester.ResponseMatcher

/**
 * Cucumber steps for OpenID Authorization
 */
class AuthorizeSteps(
        private val requester: Requester,
        private val authRedirectMatcher: ResponseMatcher) : En {
    init {
        When("I start OpenID Authorization with parameters:$") { parameters: DataTable ->
            val uri = UriComponentsBuilder.fromPath("/openid/authorize")

            parameters.asMap<String, String>(String::class.java, String::class.java)
                    .forEach {
                        uri.queryParam(it.key, it.value)
                    }

            requester.makeRequest(uri.toUriString(), HttpMethod.GET, null, String::class.java)
        }

        When("I authenticate with parameters:$") { parameters: DataTable ->
            val body = parameters.asMap<String, String>(String::class.java, String::class.java)
                    .mapValues { listOf(it.value) }

            requester.makeRequest("/openid/authorize/continue",
                    HttpMethod.POST,
                    LinkedMultiValueMap(body),
                    String::class.java)
        }

        When("I register a new user with parameters:$") { parameters: DataTable ->
            val body = parameters.asMap<String, String>(String::class.java, String::class.java)
                    .mapValues { listOf(it.value) }

            requester.makeRequest("/openid/authorize/register",
                    HttpMethod.POST,
                    LinkedMultiValueMap(body),
                    String::class.java)
        }

        When("I log in with parameters:$") { parameters: DataTable ->
            val body = parameters.asMap<String, String>(String::class.java, String::class.java)
                    .mapValues { listOf(it.value) }

            requester.makeRequest("/openid/authorize/login",
                    HttpMethod.POST,
                    LinkedMultiValueMap(body),
                    String::class.java)
        }

        Then("^I am redirected to the callback address:$") { details: DataTable ->
            authRedirectMatcher.match(details)
        }
    }
}
