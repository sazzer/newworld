package uk.co.grahamcox.worlds.service.worlds.rest

import org.springframework.http.HttpStatus
import uk.co.grahamcox.worlds.service.rest.Problem
import java.net.URI

/**
 * Problem to indicate a World that wasn't found
 */
class WorldNotFoundProblem : Problem(
        type = URI("tag:grahamcox.co.uk,2018,users/problems/world-not-found"),
        title = "World not found",
        httpStatus = HttpStatus.NOT_FOUND
)
