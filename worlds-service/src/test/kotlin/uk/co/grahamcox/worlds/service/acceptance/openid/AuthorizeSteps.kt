package uk.co.grahamcox.worlds.service.acceptance.openid

import cucumber.api.java8.En
import io.cucumber.datatable.DataTable
import org.springframework.http.HttpMethod
import org.springframework.web.util.UriComponentsBuilder
import uk.co.grahamcox.worlds.service.acceptance.requester.Requester

/**
 * Cucumber steps for OpenID Authorization
 */
class AuthorizeSteps(
        private val requester: Requester) : En {
    init {
        When("I start OpenID Authorization with parameters:$") { parameters: DataTable ->
            val uri = UriComponentsBuilder.fromPath("/openid/authorize")

            parameters.asMap<String, String>(String::class.java, String::class.java)
                    .forEach {
                        uri.queryParam(it.key, it.value)
                    }

            requester.makeRequest(uri.toUriString(), HttpMethod.GET, null, String::class.java)
        }
    }
}