package uk.co.grahamcox.worlds.service.users.rest

import org.springframework.http.HttpStatus
import uk.co.grahamcox.worlds.service.rest.Problem
import java.net.URI

/**
 * Problem to indicate a Username is already being used by a different user
 */
class DuplicateUsernameProblem : Problem(
        type = URI("tag:grahamcox.co.uk,2018,users/problems/duplicate-username"),
        title = "The specified username was not unique",
        httpStatus = HttpStatus.CONFLICT
)
