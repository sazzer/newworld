package uk.co.grahamcox.worlds.service.acceptance.requester

import cucumber.api.java8.En
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.function.Executable
import org.springframework.core.io.ResourceLoader
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.util.StreamUtils

/**
 * Cucumber steps for working directly with the requester
 */
class RequesterSteps(
        private val requester: Requester,
        private val resourceLoader: ResourceLoader
) : En {
    init {
        Before { scenario -> requester.reset() }

        Then("^I get an? (.+) response$") { statusName: String ->
            val convertedName = statusName.toUpperCase().replace(" ", "_")
            val httpStatus = HttpStatus.values().find { it.name == convertedName }

            Assertions.assertNotNull(httpStatus, "Unknown Status: $statusName")
            Assertions.assertEquals(httpStatus, requester.lastResponse.statusCode)
        }

        Then("""^the response matches snapshot "(.+)"$""") { snapshotName: String ->
            val snapshotResource = resourceLoader.getResource("classpath:$snapshotName")
            val snapshot = if (snapshotResource.exists()) {
                StreamUtils.copyToString(snapshotResource.inputStream, Charsets.UTF_8)
            } else {
                ""
            }

            val lastResponse = requester.lastResponse
            Assertions.assertAll(
                    Executable { Assertions.assertTrue(lastResponse.response.headers.contentType!!.isCompatibleWith(MediaType.TEXT_HTML)) },
                    Executable { Assertions.assertEquals(snapshot.trim(),
                            lastResponse.response.body.toString().trim(),
                            "Snapshot did not match expected content: $snapshotName") }
            )

        }
    }
}
