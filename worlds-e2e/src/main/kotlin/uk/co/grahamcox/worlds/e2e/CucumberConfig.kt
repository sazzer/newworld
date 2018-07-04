package uk.co.grahamcox.worlds.e2e

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

/**
 * The core configuration for the Cucumber tests
 */
@SpringBootConfiguration
@EnableAutoConfiguration(exclude=[
    DataSourceAutoConfiguration::class
])
@Configuration
@Import(
)
class CucumberConfig