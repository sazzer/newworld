package uk.co.grahamcox.worlds.service.rest

import org.springframework.http.HttpStatus
import java.net.URI

/**
 * Base class to represent an HTTP Problem as defined in RFC-7807
 */
open class Problem(
    val type: URI,
    val title: String,
    private val httpStatus: HttpStatus,
    val detail: String? = null,
    val instance: URI? = null
) {
    val status = httpStatus.value()
}
