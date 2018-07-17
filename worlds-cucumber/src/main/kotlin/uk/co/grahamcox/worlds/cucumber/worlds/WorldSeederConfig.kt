package uk.co.grahamcox.worlds.cucumber.worlds

import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import uk.co.grahamcox.worlds.cucumber.database.DatabaseSeeder
import uk.co.grahamcox.worlds.cucumber.database.SeedFieldConfig
import java.time.Clock
import java.time.Instant
import java.util.*

/**
 * Spring Configuration for testing of worlds
 */
@Configuration
class WorldsSeederConfig(context: GenericApplicationContext) {
    init {
        beans {
            bean("worldSeeder") {
                val clock = ref<Clock>()

                DatabaseSeeder(
                        ref(),
                        "worlds",
                        mapOf(
                                "ID" to SeedFieldConfig(
                                        fieldName = "world_id",
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
                                "Name" to SeedFieldConfig(
                                        fieldName = "name",
                                        defaultGenerator = { UUID.randomUUID() }
                                ),
                                "Display Name" to SeedFieldConfig(
                                        fieldName = "display_name",
                                        defaultGenerator = { "Test World" }
                                ),
                                "Description" to SeedFieldConfig(
                                        fieldName = "description",
                                        defaultGenerator = { "" }
                                ),
                                "Owner" to SeedFieldConfig(
                                        fieldName = "owner",
                                        valueConversion = { UUID.fromString(it) }
                                )
                        )
                )
            }
        }.initialize(context)
    }
}
