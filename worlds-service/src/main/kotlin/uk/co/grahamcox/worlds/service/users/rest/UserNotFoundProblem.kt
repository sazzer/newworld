package uk.co.grahamcox.worlds.service.users.rest

import org.springframework.http.HttpStatus
import uk.co.grahamcox.worlds.service.rest.Problem
import java.net.URI

/**
 * Problem to indicate a User that wasn't found
 */
class UserNotFoundProblem : Problem(
        type = URI("tag:grahamcox.co.uk,2018,users/problems/user-not-found"),
        title = "User not found",
        httpStatus = HttpStatus.NOT_FOUND
)
