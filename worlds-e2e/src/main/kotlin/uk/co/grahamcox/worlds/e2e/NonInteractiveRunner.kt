package uk.co.grahamcox.worlds.e2e

import org.springframework.beans.factory.InitializingBean

/**
 * Spring Runner that just runs all of the tests as configured from the properties
 */
class NonInteractiveRunner(
        private val cucumberRunner: CucumberRunner
) : InitializingBean {
    /**
     * Run the tests
     */
    override fun afterPropertiesSet() {
        val options = CucumberOptions()

        if (!cucumberRunner.run(options)) {
            throw AssertionError("Cucumber tests were unsuccessful")
        }
    }
}
