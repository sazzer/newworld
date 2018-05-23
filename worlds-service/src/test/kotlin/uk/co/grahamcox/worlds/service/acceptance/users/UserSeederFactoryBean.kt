package uk.co.grahamcox.worlds.service.acceptance.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.AbstractFactoryBean
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import uk.co.grahamcox.worlds.service.acceptance.database.DatabaseSeeder
import uk.co.grahamcox.worlds.service.acceptance.database.SeedFieldConfig
import java.time.Clock
import java.time.Instant
import java.util.*

/**
 * Construct the Database Seeder for seeding users
 */
@Component("userSeeder")
class UserSeederFactoryBean : AbstractFactoryBean<DatabaseSeeder>() {
    /** The clock to use */
    @Autowired
    private lateinit var clock: Clock

    /** The JDBC Template */
    @Autowired
    private lateinit var jdbcTemplate: NamedParameterJdbcTemplate

    override fun getObjectType() = DatabaseSeeder::class.java

    override fun createInstance(): DatabaseSeeder {
        return DatabaseSeeder(
                jdbcTemplate,
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

}
