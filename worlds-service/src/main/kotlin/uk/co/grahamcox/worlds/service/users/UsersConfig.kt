package uk.co.grahamcox.worlds.service.users

import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import uk.co.grahamcox.worlds.service.rest.hal.MvcLinkBuilder
import uk.co.grahamcox.worlds.service.users.dao.UserServiceImpl
import uk.co.grahamcox.worlds.service.users.password.PasswordHasher
import uk.co.grahamcox.worlds.service.users.password.PbePasswordHasherImpl
import uk.co.grahamcox.worlds.service.users.rest.UserController
import uk.co.grahamcox.worlds.service.users.rest.UserModelBuilder
import uk.co.grahamcox.worlds.service.worlds.rest.WorldsController

/**
 * Configuration for working with users
 */
@Configuration
class UsersConfig(context: GenericApplicationContext) {
    init {
        beans {
            bean("userLinkBuilder") {
                MvcLinkBuilder<String>(UserController::getUser)
            }
            bean<UserServiceImpl>()
            bean<UserModelBuilder>()
            bean<UserController>()

            bean<PasswordHasher> {
                PbePasswordHasherImpl(
                        hasherName = "PBKDF2WithHmacSHA512",
                        saltSize = 128,
                        iterations = 10000,
                        keyLength = 256
                )
            }

            bean<PasswordChangerImpl>()
        }.initialize(context)
    }
}
