package uk.co.grahamcox.worlds.service.users.rest

import org.springframework.http.HttpStatus
import uk.co.grahamcox.worlds.service.rest.Problem
import java.net.URI

/**
 * Problem to indicate an Email Address is already being used by a different user
 */
class DuplicateEmailProblem : Problem(
        type = URI("tag:grahamcox.co.uk,2018,users/problems/duplicate-email"),
        title = "The specified email address was not unique",
        httpStatus = HttpStatus.CONFLICT
)
