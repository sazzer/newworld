package uk.co.grahamcox.worlds.service.users.rest

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import uk.co.grahamcox.worlds.service.model.Resource
import uk.co.grahamcox.worlds.service.openid.authorization.Authorizer
import uk.co.grahamcox.worlds.service.openid.rest.AccessTokenHolder
import uk.co.grahamcox.worlds.service.openid.token.AccessToken
import uk.co.grahamcox.worlds.service.rest.schemaLink
import uk.co.grahamcox.worlds.service.users.*

/**
 * Controller for working with Users
 */
@RestController
@RequestMapping("/api/users")
class UserController(
        private val userService: UserService,
        private val accessTokenHolder: AccessTokenHolder
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

        val updatedUserData = UserData(
                email = updated.email,
                displayName = updated.displayName,
                username = updated.username,
                password = user.data.password
        )

        val updatedUser = userService.update(userId, updatedUserData)
        return buildUserResponse(updatedUser)
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
                .body(translateUser(user))
    }

    /**
     * Translate a User resource into the REST version
     */
    private fun translateUser(user: Resource<UserId, UserData>): UserModel {
        val currentUser = accessTokenHolder.accessToken?.user

        return UserModel(
                id = user.identity.id.id,
                displayName = user.data.displayName,
                email = if (currentUser == user.identity.id) user.data.email else null,
                username = user.data.username
        )
    }
}
