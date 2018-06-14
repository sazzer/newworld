package uk.co.grahamcox.worlds.e2e

import cucumber.runtime.Runtime
import cucumber.runtime.RuntimeOptions
import cucumber.runtime.io.MultiLoader
import cucumber.runtime.io.ResourceLoaderClassFinder
import cucumber.runtime.model.CucumberFeature
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
    private fun buildOptions(options: CucumberOptions): RuntimeOptions {
        val args = mutableListOf<String>()

        if (options.dryRun) {
            args.add("--dry-run")
        }

        if (options.strict) {
            args.add("--strict")
        }

        options.tags.forEach { tag ->
            args.add("--tags")
            args.add(tag)
        }

        options.names.forEach { name ->
            args.add("--name")
            args.add(name)
        }

        args.add("--glue")
        args.add("classpath:uk/co/grahamcox/worlds/e2e")

        args.add("--plugin")
        args.add("pretty")

        options.paths.forEach { path ->
            args.add(path)
        }

        if (options.paths.isEmpty()) {
            args.add("classpath:uk/co/grahamcox/worlds/e2e/features")
        }

        return RuntimeOptions(args)
    }

    /**
     * Build the Runtime to use
     */
    private fun buildRuntime(runtimeOptions: RuntimeOptions) =
            Runtime(resourceLoader, classFinder, classLoader, runtimeOptions)

    /**
     * Get the features that we are going to work with
     */
    fun getFeatures(): List<CucumberFeature> {
        val runtimeOptions = buildOptions(CucumberOptions())
        val runtime = buildRuntime(runtimeOptions)

        return runtimeOptions.cucumberFeatures(resourceLoader, runtime.eventBus)
    }

    /**
     * Run the matching scenarios
     * @return true if the tests were successful. False if not
     */
    fun run(options: CucumberOptions): Boolean {
        LOG.info("Running with options: {}", options)

        val runtimeOptions = buildOptions(options)
        val runtime = buildRuntime(runtimeOptions)

        runtime.run()

        return runtime.exitStatus().toInt() == 0
    }
}
