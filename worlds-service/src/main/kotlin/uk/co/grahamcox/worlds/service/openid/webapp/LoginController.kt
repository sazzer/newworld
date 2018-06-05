package uk.co.grahamcox.worlds.service.openid.webapp

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import uk.co.grahamcox.worlds.service.openid.responseTypes.ResponseTypes
import uk.co.grahamcox.worlds.service.openid.scopes.ScopeRegistry
import uk.co.grahamcox.worlds.service.users.DuplicateUsernameException
import uk.co.grahamcox.worlds.service.users.UserData
import uk.co.grahamcox.worlds.service.users.UserRetriever
import uk.co.grahamcox.worlds.service.users.UserService
import uk.co.grahamcox.worlds.service.users.password.PasswordHasher

/**
 * Controller for handling user login as part of authenticating a user
 */
@Controller
@RequestMapping("/openid/authorize/login", method = [RequestMethod.POST])
class LoginController(
        private val userRetriever: UserRetriever,
        private val passwordHasher: PasswordHasher,
        private val redirectUriBuilder: RedirectUriBuilder,
        scopeRegistry: ScopeRegistry,
        supportedResponseTypes: Map<String, Set<ResponseTypes>>
) : AuthorizeControllerBase(scopeRegistry, supportedResponseTypes)  {
    /**
     * Handler to log in an existing user, or display an error if login fails for some reason
     */
    @RequestMapping
    fun register(command: AuthorizeCommand,
                 @RequestParam("email") email: String,
                 @RequestParam("password") password: String?): Any {

        verifyCommand(command)

        // First, check if the email address exists. If so then display the Login form instead, with a message indicating
        // what's happened
        val user = userRetriever.getByEmail(email)

        return if (user == null) {
            ModelAndView("/openid/register", mapOf(
                    "parameters" to command,
                    "email" to email,
                    "email_unknown_error" to true
            ))
        } else {
            // Next, perform some validation of our inputs.
            val errors = mapOf(
                    "blank_password" to password.isNullOrBlank()
            ).filter { it.value }

            if (errors.isNotEmpty()) {
                // If any of this validation fails then display the Register form again, with the error messages
                ModelAndView("/openid/login", mapOf(
                        "parameters" to command,
                        "email" to email
                ) + errors)
            } else {
                val inputPassword = passwordHasher.hashPassword(password!!, user.data.password.salt)

                if (inputPassword != user.data.password) {
                    // Password mismatch
                    ModelAndView("/openid/login", mapOf(
                            "parameters" to command,
                            "email" to email,
                            "invalid_password" to true
                    ))
                } else {

                    return ResponseEntity.status(HttpStatus.SEE_OTHER)
                            .location(redirectUriBuilder.buildUri(command, user))
                            .build<Unit>()                }
            }
        }
    }
}
