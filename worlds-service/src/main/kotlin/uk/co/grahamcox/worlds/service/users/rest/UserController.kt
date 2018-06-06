package uk.co.grahamcox.worlds.service.users.rest

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import uk.co.grahamcox.worlds.service.model.Resource
import uk.co.grahamcox.worlds.service.openid.rest.AccessTokenHolder
import uk.co.grahamcox.worlds.service.users.UserData
import uk.co.grahamcox.worlds.service.users.UserId
import uk.co.grahamcox.worlds.service.users.UserNotFoundException
import uk.co.grahamcox.worlds.service.users.UserRetriever

/**
 * Controller for working with Users
 */
@RestController
@RequestMapping("/api/users")
class UserController(
        private val userRetriever: UserRetriever,
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
     * Get a single User by ID
     * @param id The ID of the user
     * @return the response entity representing the user
     */
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getUser(@PathVariable("id") id: String): ResponseEntity<UserModel> {
        val user = userRetriever.getById(UserId(id))

        return ResponseEntity.status(HttpStatus.OK)
                .eTag(user.identity.version)
                .lastModified(user.identity.updated.toEpochMilli())
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
