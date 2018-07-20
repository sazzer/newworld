package uk.co.grahamcox.worlds.service.worlds.rest

import uk.co.grahamcox.worlds.service.model.Resource
import uk.co.grahamcox.worlds.service.rest.ModelBuilder
import uk.co.grahamcox.worlds.service.rest.hal.LinkBuilder
import uk.co.grahamcox.worlds.service.worlds.WorldData
import uk.co.grahamcox.worlds.service.worlds.WorldId

/**
 * Helper to build a World Model from an internal World resource
 */
class WorldModelBuilder(
        private val worldLinkBuilder: LinkBuilder<String>,
        private val userLinkBuilder: LinkBuilder<String>
) : ModelBuilder<WorldId, WorldData, WorldModel> {
    /**
     * Build the World Model from the internal resource
     * @param input The input to build the model from
     * @return the model
     */
    override fun build(input: Resource<WorldId, WorldData>) : WorldModel {
        return WorldModel(
                id = input.identity.id.id,
                name = input.data.name,
                displayName = input.data.displayName,
                description = input.data.description,
                owner = input.data.owner.id,
                links = WorldModelLinks(
                        self = worldLinkBuilder.buildLink(input.identity.id.id),
                        owner = userLinkBuilder.buildLink(input.data.owner.id)
                )
        )
    }
}
