package uk.co.grahamcox.worlds.service.worlds.rest

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import uk.co.grahamcox.worlds.service.model.Resource
import uk.co.grahamcox.worlds.service.rest.ModelBuilder
import uk.co.grahamcox.worlds.service.rest.schemaLink
import uk.co.grahamcox.worlds.service.worlds.WorldData
import uk.co.grahamcox.worlds.service.worlds.WorldId
import uk.co.grahamcox.worlds.service.worlds.WorldNotFoundException
import uk.co.grahamcox.worlds.service.worlds.WorldService

/**
 * Controller for working with Worlds
 */
@RestController
@RequestMapping("/api/worlds")
class WorldsController(
        private val worldService: WorldService,
        private val worldModelBuilder: ModelBuilder<WorldId, WorldData, WorldModel>
) {
    /**
     * Handle a World Not Found error
     */
    @ExceptionHandler(WorldNotFoundException::class)
    fun handleWorldNotFound(): ResponseEntity<WorldNotFoundProblem> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.parseMediaType("application/problem+json"))
                .body(WorldNotFoundProblem())
    }

    /**
     * Get a single World by ID
     * @param id The ID of the world
     * @return the response entity representing the world
     */
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getWorld(@PathVariable("id") id: String): ResponseEntity<WorldModel> {
        val world = worldService.getById(WorldId(id))

        return buildWorldResponse(world)
    }

    /**
     * Build the response to send back to the client for a World
     * @param world The world to send back
     * @return the actual response entity representing the world
     */
    private fun buildWorldResponse(world: Resource<WorldId, WorldData>): ResponseEntity<WorldModel> {
        return ResponseEntity.status(HttpStatus.OK)
                .eTag(world.identity.version)
                .lastModified(world.identity.updated.toEpochMilli())
                .schemaLink("/schema/worlds/world.json")
                .body(worldModelBuilder.build(world))
    }

}
