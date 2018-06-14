package uk.co.grahamcox.worlds.e2e

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans

@SpringBootConfiguration
@EnableAutoConfiguration(exclude=[
    DataSourceAutoConfiguration::class
])
@Import(
        NonInteractiveConfig::class
)
class WorldsE2eApplication(context: GenericApplicationContext) {
    init {
        beans {
            bean<CucumberRunner>()
            bean<CucumberCommand>()
        }.initialize(context)
    }
}

@Configuration
class NonInteractiveConfig {
    @Bean
    @ConditionalOnProperty(
            prefix = "spring.shell.interactive",
            value = ["enabled"],
            havingValue = "false",
            matchIfMissing = false
    )
    fun nonInteractiveRunner(cucumberRunner: CucumberRunner): NonInteractiveRunner {
        return NonInteractiveRunner(cucumberRunner)
    }
}

fun main(args: Array<String>) {
    runApplication<WorldsE2eApplication>(*args)
}
