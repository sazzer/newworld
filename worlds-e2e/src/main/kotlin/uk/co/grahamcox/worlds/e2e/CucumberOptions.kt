package uk.co.grahamcox.worlds.e2e

/**
 * Options to apply when running the tests
 */
data class CucumberOptions(
        val tags: List<String> = listOf(),
        val names: List<String> = listOf(),
        val paths: List<String> = listOf(),
        val dryRun: Boolean = false,
        val strict: Boolean = true
)
