package uk.co.grahamcox.worlds.service.worlds

import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import uk.co.grahamcox.worlds.service.rest.hal.MvcLinkBuilder
import uk.co.grahamcox.worlds.service.worlds.dao.WorldServiceImpl
import uk.co.grahamcox.worlds.service.worlds.rest.WorldModelBuilder
import uk.co.grahamcox.worlds.service.worlds.rest.WorldsController

/**
 * Configuration for working with worlds
 */
@Configuration
class WorldsConfig(context: GenericApplicationContext) {
    init {
        beans {
            bean("worldLinkBuilder") {
                MvcLinkBuilder<String>(WorldsController::getWorld)
            }
            bean<WorldServiceImpl>()
            bean<WorldModelBuilder>()
            bean<WorldsController>()
        }.initialize(context)
    }
}
