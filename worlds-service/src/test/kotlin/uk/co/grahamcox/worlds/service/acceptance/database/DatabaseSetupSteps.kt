package uk.co.grahamcox.worlds.service.acceptance.database

import cucumber.api.java8.En

/**
 * Cucumber steps to work with the database
 */
class DatabaseSetupSteps(
        private val cleaner: DatabaseCleaner
) : En {
    init {
        Before { scenario ->
            cleaner.clean()
        }
    }
}
