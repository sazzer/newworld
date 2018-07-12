package uk.co.grahamcox.worlds.service.worlds

import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans

/**
 * Configuration for working with worlds
 */
@Configuration
class WorldsConfig(context: GenericApplicationContext) {
    init {
        beans {
        }.initialize(context)
    }
}
