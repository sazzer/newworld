package uk.co.grahamcox.worlds.service.users.rest

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import uk.co.grahamcox.worlds.service.model.Resource
import uk.co.grahamcox.worlds.service.openid.authorization.Authorizer
import uk.co.grahamcox.worlds.service.rest.ModelBuilder
import uk.co.grahamcox.worlds.service.rest.schemaLink
import uk.co.grahamcox.worlds.service.users.*

/**
 * Controller for working with Users
 */
@RestController
@RequestMapping("/api/users")
class UserController(
        private val userService: UserService,
        private val passwordChanger: PasswordChanger,
        private val userModelBuilder: ModelBuilder<UserId, UserData, UserModel>
) {
    /**
     * Handle a User Not Found error
     */
    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(): ResponseEntity<UserNotFoundProblem> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.parseMediaType("application/problem+json"))
                .body(UserNotFoundProblem())
    }

    /**
     * Handle a Duplicate Username error
     */
    @ExceptionHandler(DuplicateUsernameException::class)
    fun handleDuplicateUsername(): ResponseEntity<DuplicateUsernameProblem> {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.parseMediaType("application/problem+json"))
                .body(DuplicateUsernameProblem())
    }

    /**
     * Handle a Duplicate Email error
     */
    @ExceptionHandler(DuplicateEmailException::class)
    fun handleDuplicateEmail(): ResponseEntity<DuplicateEmailProblem> {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.parseMediaType("application/problem+json"))
                .body(DuplicateEmailProblem())
    }

    /**
     * Handle an Invalid Password  error
     */
    @ExceptionHandler(InvalidPasswordException::class)
    fun handleInvalidPassword(): ResponseEntity<InvalidPasswordProblem> {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.parseMediaType("application/problem+json"))
                .body(InvalidPasswordProblem())
    }

    /**
     * Get a single User by ID
     * @param id The ID of the user
     * @return the response entity representing the user
     */
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getUser(@PathVariable("id") id: String): ResponseEntity<UserModel> {
        val user = userService.getById(UserId(id))

        return buildUserResponse(user)
    }

    /**
     * Update a single User by ID
     * @param id The ID of the user
     * @param authorizer The means to check that the user can perform this action
     * @param updated The updated representation of the user
     * @return the response entity representing the user
     */
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.PUT])
    fun updateUser(@PathVariable("id") id: String,
                   authorizer: Authorizer,
                   @RequestBody updated: UserInputModel): ResponseEntity<UserModel> {
        val userId = UserId(id)

        authorizer.sameUser(userId)

        val user = userService.getById(userId)

        val updatedUserData = user.data.copy(
                email = updated.email,
                displayName = updated.displayName,
                username = updated.username
        )

        val updatedUser = userService.update(userId, updatedUserData)
        return buildUserResponse(updatedUser)
    }

    /**
     * Change the password of a single user
     * @param id The ID of the user
     * @param authorizer The means to check that the user can perform this action
     * @param changePasswordInputModel The description needed to change the password
     */
    @RequestMapping(value = ["/{id}/password"], method = [RequestMethod.PUT])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changePassword(@PathVariable("id") id: String,
                   authorizer: Authorizer,
                   @RequestBody changePasswordInputModel: ChangePasswordInputModel) {
        val userId = UserId(id)

        authorizer.sameUser(userId)

        passwordChanger.changePassword(userId,
                changePasswordInputModel.oldPassword,
                changePasswordInputModel.newPassword)
    }

    /**
     * Build the response to send back to the client for a User
     * @param user The user to send back
     * @return the actual response entity representing the user
     */
    private fun buildUserResponse(user: Resource<UserId, UserData>): ResponseEntity<UserModel> {
        return ResponseEntity.status(HttpStatus.OK)
                .eTag(user.identity.version)
                .lastModified(user.identity.updated.toEpochMilli())
                .schemaLink("/schema/users/user.json")
                .body(userModelBuilder.build(user))
    }
}
