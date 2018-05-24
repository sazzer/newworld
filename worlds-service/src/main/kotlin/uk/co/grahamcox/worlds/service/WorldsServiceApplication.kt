package uk.co.grahamcox.worlds.service

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import uk.co.grahamcox.worlds.service.spring.WorldsConfiguration

@SpringBootConfiguration
@EnableAutoConfiguration
@EnableJpaRepositories
@Import(
        WorldsConfiguration::class
)
class WorldsServiceApplication

fun main(args: Array<String>) {
    runApplication<WorldsServiceApplication>(*args)
}
