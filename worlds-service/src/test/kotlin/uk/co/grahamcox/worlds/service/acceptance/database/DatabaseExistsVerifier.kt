package uk.co.grahamcox.worlds.service.acceptance.database

import org.junit.jupiter.api.Assertions
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

/**
 * Mechanism to check if records do or do not exist in the database
 */
class DatabaseExistsVerifier(
        private val jdbcTemplate: NamedParameterJdbcTemplate,
        private val sql: String
) {
    /**
     * Check that exactly one record exists that matches the given input value
     */
    fun checkExists(input: String) {
        val count = jdbcTemplate.queryForObject(sql, mapOf("id" to input), Integer::class.java)

        Assertions.assertEquals(1, count)
    }

    /**
     * Check that exactly zero records exist that matches the given input value
     */
    fun checkNotExists(input: String) {
        val count = jdbcTemplate.queryForObject(sql, mapOf("id" to input), Integer::class.java)

        Assertions.assertEquals(0, count)
    }
}
