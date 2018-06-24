package uk.co.grahamcox.worlds.service.users.rest

import org.springframework.http.HttpStatus
import uk.co.grahamcox.worlds.service.rest.Problem
import java.net.URI

/**
 * Problem to indicate a Users Password was incorrect
 */
class InvalidPasswordProblem : Problem(
        type = URI("tag:grahamcox.co.uk,2018,users/problems/invalid-password"),
        title = "Invalid Password",
        httpStatus = HttpStatus.CONFLICT
)
