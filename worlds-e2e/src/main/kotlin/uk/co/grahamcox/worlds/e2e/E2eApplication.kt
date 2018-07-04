package uk.co.grahamcox.worlds.e2e

/**
 * The actual main method for the E2E Tests
 */
fun main(args: Array<String>) {
    val runner = CucumberRunner()
    runner.run()
}
