package uk.co.grahamcox.worlds.service.spring

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import uk.co.grahamcox.worlds.service.SchemaController
import uk.co.grahamcox.worlds.service.database.DatabaseConfig
import uk.co.grahamcox.worlds.service.home.HomeController
import uk.co.grahamcox.worlds.service.home.LinkParams
import uk.co.grahamcox.worlds.service.openid.OpenIdConfig
import uk.co.grahamcox.worlds.service.rest.ValidationControllerAdvice
import uk.co.grahamcox.worlds.service.users.UsersConfig
import uk.co.grahamcox.worlds.service.users.rest.UserController
import uk.co.grahamcox.worlds.service.worlds.WorldsConfig
import uk.co.grahamcox.worlds.service.worlds.rest.WorldsController
import java.time.Clock

@Configuration
@Import(
        DatabaseConfig::class,
        UsersConfig::class,
        WorldsConfig::class,
        OpenIdConfig::class,
        WebMvcConfig::class
)
class WorldsConfiguration(context: GenericApplicationContext) {

    init {
        beans {
            bean("clock") {
                Clock.systemUTC()
            }

            bean<SchemaController>()
            bean {
                HomeController(listOf(
                        LinkParams(
                                name = "tag:grahamcox.co.uk,2018,links/user",
                                handler = UserController::getUser,
                                params = mapOf("id" to "{id}")
                        ),
                        LinkParams(
                                name = "tag:grahamcox.co.uk,2018,links/world",
                                handler = WorldsController::getWorld,
                                params = mapOf("id" to "{id}")
                        )
                ))
            }

            bean<ValidationControllerAdvice>()
        }.initialize(context)
    }

}
