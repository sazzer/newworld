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
import uk.co.grahamcox.worlds.service.users.UserService
import uk.co.grahamcox.worlds.service.users.password.PasswordHasher

/**
 * Controller for handling user registration as part of authenticating a user
 */
@Controller
@RequestMapping("/openid/authorize/register", method = [RequestMethod.POST])
class RegisterController(
        private val userService: UserService,
        private val passwordHasher: PasswordHasher,
        private val redirectUriBuilder: RedirectUriBuilder,
        scopeRegistry: ScopeRegistry,
        supportedResponseTypes: Map<String, Set<ResponseTypes>>
) : AuthorizeControllerBase(scopeRegistry, supportedResponseTypes)  {
    /**
     * Handler to register a new user, or display an error if registration fails for some reason
     */
    @RequestMapping
    fun register(command: AuthorizeCommand,
                 @RequestParam("email") email: String,
                 @RequestParam("password") password: String?,
                 @RequestParam("password2") password2: String?,
                 @RequestParam("username") username: String?,
                 @RequestParam("display_name") displayName: String?): Any {

        verifyCommand(command)

        // First, check if the email address exists. If so then display the Login form instead, with a message indicating
        // what's happened
        val user = userService.getByEmail(email)

        return if (user != null) {
            ModelAndView("/openid/login", mapOf(
                    "parameters" to command,
                    "email" to email,
                    "email_exists_error" to true
            ))
        } else {
            // Next, perform some validation of our inputs.
            val errors = mapOf(
                    "blank_password" to password.isNullOrBlank(),
                    "password_mismatch" to (password != password2),
                    "blank_username" to username.isNullOrBlank()
            ).filter { it.value }

            if (errors.isNotEmpty()) {
                // If any of this validation fails then display the Register form again, with the error messages
                ModelAndView("/openid/register", mapOf(
                        "parameters" to command,
                        "email" to email,
                        "username" to username,
                        "displayName" to displayName
                ) + errors)
            } else {
                // Otherwise proceed to actually registering the user
                try {
                    val createdUser = userService.create(UserData(
                            email = email,
                            username = username!!,
                            displayName = if (displayName.isNullOrBlank()) username else displayName!!,
                            password = passwordHasher.hashPassword(password!!)
                    ))

                    return ResponseEntity.status(HttpStatus.SEE_OTHER)
                            .location(redirectUriBuilder.buildUri(command, createdUser))
                            .build<Unit>()
                } catch (e : DuplicateUsernameException) {
                    ModelAndView("/openid/register", mapOf(
                            "parameters" to command,
                            "email" to email,
                            "username" to username,
                            "displayName" to displayName,
                            "duplicate_username" to true
                    ))
                }
            }

        }
    }
}
