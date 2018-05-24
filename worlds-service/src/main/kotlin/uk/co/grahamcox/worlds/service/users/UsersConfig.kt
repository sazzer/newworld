package uk.co.grahamcox.worlds.service.users

import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import uk.co.grahamcox.worlds.service.users.rest.UserController

/**
 * Configuration for working with users
 */
@Configuration
class UsersConfig(context: GenericApplicationContext) {
    init {
        beans {
            bean<UserServiceImpl>()
            bean<UserController>()
        }.initialize(context)
    }
}
