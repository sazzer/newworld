package uk.co.grahamcox.worlds.service.acceptance.database

import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

/**
 * Configuration of a single field to seed
 */
data class SeedFieldConfig(
        val fieldName: String,
        val valueConversion: (String) -> Any = { it -> it },
        val defaultGenerator: () -> Any? = { null }
)

/**
 * Mechanism to seed a table in the database
 */
class DatabaseSeeder(
        private val jdbcTemplate: NamedParameterJdbcTemplate,
        private val table: String,
        private val fields: Map<String, SeedFieldConfig>
) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(DatabaseSeeder::class.java)
    }

    /**
     * Seed the database with the provided data
     */
    fun seed(input: Map<String, String>) {
        val defaults = fields
                .filterNot { input.containsKey(it.key) }
                .toList()
                .map { it.second }
                .map {
                    it.fieldName to it.defaultGenerator()
                }
                .toMap()
        LOG.debug("Default values to use: {}", defaults)

        val binds = fields
                .filter { input.containsKey(it.key) }
                .toList()
                .map { (key, config) ->
                    config.fieldName to config.valueConversion(input[key]!!)
                }
                .toMap()
        LOG.debug("Real binds to use: {}", binds)

        val columns = fields
                .toList()
                .map { it.second.fieldName }
        val inputs = columns.map { ":$it" }
        val sql = "INSERT INTO $table (${columns.joinToString(", ")}) VALUES (${inputs.joinToString(", ")})"
        LOG.debug("SQL to execute: {}", sql)

        jdbcTemplate.update(sql, defaults + binds)
    }
}
