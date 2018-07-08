package uk.co.grahamcox.worlds.cucumber.users

import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import uk.co.grahamcox.worlds.cucumber.database.DatabaseSeeder
import uk.co.grahamcox.worlds.cucumber.database.SeedFieldConfig
import java.time.Clock
import java.time.Instant
import java.util.*

/**
 * Spring Configuration for testing of users
 */
@Configuration
class UsersSeederConfig(context: GenericApplicationContext) {
    init {
        beans {
            bean("userSeeder") {
                val clock = ref<Clock>()

                DatabaseSeeder(
                        ref(),
                        "users",
                        mapOf(
                                "ID" to SeedFieldConfig(
                                        fieldName = "user_id",
                                        defaultGenerator = { UUID.randomUUID() },
                                        valueConversion = { UUID.fromString(it) }
                                ),
                                "Version" to SeedFieldConfig(
                                        fieldName = "version",
                                        defaultGenerator = { UUID.randomUUID() },
                                        valueConversion = { UUID.fromString(it) }
                                ),
                                "Created" to SeedFieldConfig(
                                        fieldName = "created",
                                        defaultGenerator = { Date.from(clock.instant()) },
                                        valueConversion = { Date.from(Instant.parse(it)) }
                                ),
                                "Updated" to SeedFieldConfig(
                                        fieldName = "updated",
                                        defaultGenerator = { Date.from(clock.instant()) },
                                        valueConversion = { Date.from(Instant.parse(it)) }
                                ),
                                "Email" to SeedFieldConfig(
                                        fieldName = "email",
                                        defaultGenerator = { "${UUID.randomUUID()}@example.com" }
                                ),
                                "Username" to SeedFieldConfig(
                                        fieldName = "username",
                                        defaultGenerator = { UUID.randomUUID().toString() }
                                ),
                                "Display Name" to SeedFieldConfig(
                                        fieldName = "display_name",
                                        defaultGenerator = { "Test User" }
                                ),
                                "Password Hash" to SeedFieldConfig(
                                        fieldName = "password_hash",
                                        defaultGenerator = { "" }
                                ),
                                "Password Salt" to SeedFieldConfig(
                                        fieldName = "password_salt",
                                        defaultGenerator = { "" }
                                )
                        )
                )
            }
        }.initialize(context)
    }
}
