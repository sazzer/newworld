package uk.co.grahamcox.worlds.service.rest

import org.springframework.http.HttpStatus
import uk.co.grahamcox.worlds.service.rest.Problem
import java.net.URI

/**
 * Problem to indicate a required parameter was missing
 * @property parameter The parameter that was missing
 */
class MissingParameterProblem(val parameter: String) : Problem(
        type = URI("tag:grahamcox.co.uk,2018,problems/missing-parameter"),
        title = "Required parameter was not present",
        httpStatus = HttpStatus.BAD_REQUEST
)
