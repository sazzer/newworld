package uk.co.grahamcox.worlds.service.acceptance.users

import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.http.HttpMethod
import uk.co.grahamcox.worlds.service.acceptance.database.DatabaseExistsVerifier
import uk.co.grahamcox.worlds.cucumber.database.DatabaseSeeder
import uk.co.grahamcox.worlds.cucumber.database.SeedFieldConfig
import uk.co.grahamcox.worlds.service.acceptance.requester.*
import java.time.Clock
import java.time.Instant
import java.util.*

/**
 * Spring Configuration for testing of users
 */
@Configuration
class UsersConfig(context: GenericApplicationContext) {
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

            bean("userResponseMatcher") {
                ResponseMatcher(
                        ref(),
                        mapOf(
                                "ID" to ResponseFieldConfig(
                                        fieldPath = "body/id"
                                ),
                                "ETag" to ResponseFieldConfig(
                                        fieldPath = "headers/etag",
                                        expectedConversion = { listOf(it) }
                                ),
                                "Last Modified" to ResponseFieldConfig(
                                        fieldPath = "headers/last-modified",
                                        expectedConversion = { listOf(it) }
                                ),
                                "Email" to ResponseFieldConfig(
                                        fieldPath = "body/email",
                                        expectedConversion = {
                                            when (it) {
                                                "[null]" -> null
                                                else -> it
                                            }
                                        }
                                ),
                                "Username" to ResponseFieldConfig(
                                        fieldPath = "body/username"
                                ),
                                "Display Name" to ResponseFieldConfig(
                                        fieldPath = "body/display_name"
                                )
                        ))
            }

            bean("userEmailExistsVerifier") {
                DatabaseExistsVerifier(
                        ref(),
                        "SELECT COUNT(*) FROM users WHERE email = :id"
                )
            }

            bean("userRetrievalRequestSubmitter") {
                RequestSubmitter(
                        ref(),
                        "/api/users/{id}",
                        HttpMethod.GET,
                        mapOf(
                                "ID" to RequestFieldConfig(
                                        fieldName = "id"
                                )
                        ),
                        mapOf()
                )
            }

            bean("userUpdateRequestSubmitter") {
                RequestSubmitter(
                        ref(),
                        "/api/users/{id}",
                        HttpMethod.PUT,
                        mapOf(
                                "ID" to RequestFieldConfig("id")
                        ),
                        mapOf(
                                "Email" to RequestFieldConfig("email"),
                                "Username" to RequestFieldConfig("username"),
                                "Display Name" to RequestFieldConfig("display_name")
                        )
                )
            }

            bean("changePasswordRequestSubmitter") {
                RequestSubmitter(
                        ref(),
                        "/api/users/{id}/password",
                        HttpMethod.PUT,
                        mapOf(
                                "ID" to RequestFieldConfig("id")
                        ),
                        mapOf(
                                "Old" to RequestFieldConfig("old_password"),
                                "New" to RequestFieldConfig("new_password")
                        )
                )
            }
        }.initialize(context)
    }
}
