package uk.co.grahamcox.worlds.e2e

import org.assertj.core.api.Assertions
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

        val result = cucumberRunner.run(options)
        Assertions.assertThat(result)
                .`as`("The cucumber tests did not run successfully")
                .isTrue()
    }
}
