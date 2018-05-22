package uk.co.grahamcox.worlds.service.acceptance.requester

import org.springframework.http.ResponseEntity

/**
 * Response from the Requester that makes checking the contents easier
 * @property response The actual response
 */
data class Response(
        val response: ResponseEntity<Map<String, Any?>>
) {
    /** The HTTP Status Code returned */
    val statusCode = response.statusCode
}
