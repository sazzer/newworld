package uk.co.grahamcox.worlds.service.acceptance

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import uk.co.grahamcox.worlds.cucumber.database.DatabaseCleaner
import uk.co.grahamcox.worlds.cucumber.users.UsersSeederConfig
import uk.co.grahamcox.worlds.service.acceptance.openid.AuthorizeConfig
import uk.co.grahamcox.worlds.service.acceptance.requester.RequesterConfig
import uk.co.grahamcox.worlds.service.acceptance.users.UsersConfig

/**
 * Base spring configuration for all of the acceptance tests
 */
@Configuration
@Import(
        RequesterConfig::class,
        UsersSeederConfig::class,
        UsersConfig::class,
        AuthorizeConfig::class
)
class CucumberConfig(context: GenericApplicationContext) {
    init {
        beans {
            bean<DatabaseCleaner>()
        }.initialize(context)
    }
}
