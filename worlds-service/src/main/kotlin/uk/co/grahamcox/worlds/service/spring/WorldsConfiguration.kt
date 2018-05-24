package uk.co.grahamcox.worlds.service.spring

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import uk.co.grahamcox.worlds.service.SchemaController
import uk.co.grahamcox.worlds.service.database.DatabaseConfig
import uk.co.grahamcox.worlds.service.openid.OpenIdConfig
import uk.co.grahamcox.worlds.service.users.UsersConfig
import java.time.Clock

@Configuration
@Import(
        DatabaseConfig::class,
        UsersConfig::class,
        OpenIdConfig::class
)
class WorldsConfiguration(context: GenericApplicationContext) {

    init {
        beans {
            bean("clock") {
                Clock.systemUTC()
            }

            bean<SchemaController>()
        }.initialize(context)
    }

}
