package uk.co.grahamcox.worlds.e2e

import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

/**
 * Shell command for working with Cucumber
 */
@ShellComponent
class CucumberCommand(private val cucumberRunner: CucumberRunner) {
    /**
     * Command to list the features that can be run
     */
    @ShellMethod(value = "List the features that we can run", key = ["features"])
    fun listFeatures() {
        cucumberRunner.getFeatures()
                .map { feature -> feature }
                .forEach { feature ->
                    val url = feature.uri.removePrefix("uk/co/grahamcox/worlds/e2e/features/")
                    val name = feature.gherkinFeature.feature.name
                    val tags = feature.gherkinFeature.feature.tags.map { tag -> tag.name }.joinToString(", ")
                    println("$url = $name ($tags)")
                }
    }

    /**
     * Command to list the scenarios that can be run
     */
    @ShellMethod(value = "List the scenarios that we can run", key = ["scenarios"])
    fun listScenarios() {
        cucumberRunner.getFeatures()
                .map { feature -> feature }
                .forEach { feature ->
                    val url = feature.uri.removePrefix("uk/co/grahamcox/worlds/e2e/features/")
                    val name = feature.gherkinFeature.feature.name
                    println("$url = $name")

                    feature.gherkinFeature.feature.children.forEach { scenario ->
                        println("Scenario: ${scenario.name}")
                    }
                }
    }

    /**
     * Command to run the scenarios
     */
    @ShellMethod(value = "Run the matching scenarios", key = ["run"])
    fun run(
            @ShellOption(value = ["--tags"], defaultValue = "") tags: List<String>?,
            @ShellOption(value = ["--names"], defaultValue = "") names: List<String>?,
            @ShellOption(value = ["--paths"], defaultValue = "") paths: List<String>?
    ) {
        cucumberRunner.run(CucumberOptions(
                tags = tags ?: listOf(),
                names = names ?: listOf(),
                paths = paths?.map { "classpath:uk/co/grahamcox/worlds/e2e/features/$it" } ?: listOf()
        ))
    }

}
