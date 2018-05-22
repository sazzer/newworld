package uk.co.grahamcox.worlds.service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class WorldsServiceApplication

fun main(args: Array<String>) {
    runApplication<WorldsServiceApplication>(*args)
}
