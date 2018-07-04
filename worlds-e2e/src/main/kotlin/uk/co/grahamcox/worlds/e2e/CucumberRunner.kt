package uk.co.grahamcox.worlds.e2e

import cucumber.runtime.Runtime
import cucumber.runtime.RuntimeOptions
import cucumber.runtime.io.MultiLoader
import cucumber.runtime.io.ResourceLoaderClassFinder
import org.slf4j.LoggerFactory

/**
 * Mechanism by which the Cucumber features can be discovered and run
 */
class CucumberRunner {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(CucumberRunner::class.java)
    }

    private val cls = CucumberRunner::class.java
    private val classLoader = cls.classLoader

    private val resourceLoader = MultiLoader(classLoader)
    private val classFinder = ResourceLoaderClassFinder(resourceLoader, classLoader)

    /**
     * Build the Runtime Options to use
     */
    private fun buildOptions(): RuntimeOptions {
        val args = mutableListOf<String>()

        args.add("--glue")
        args.add("classpath:uk/co/grahamcox/worlds/e2e")

        args.add("--plugin")
        args.add("pretty")

        args.add("classpath:uk/co/grahamcox/worlds/e2e/features")

        return RuntimeOptions(args)
    }

    /**
     * Build the Runtime to use
     */
    private fun buildRuntime(runtimeOptions: RuntimeOptions) =
            Runtime(resourceLoader, classFinder, classLoader, runtimeOptions)

    /**
     * Run the matching scenarios
     * @return true if the tests were successful. False if not
     */
    fun run(): Boolean {
        val runtimeOptions = buildOptions()
        val runtime = buildRuntime(runtimeOptions)

        runtime.run()

        return runtime.exitStatus().toInt() == 0
    }
}
