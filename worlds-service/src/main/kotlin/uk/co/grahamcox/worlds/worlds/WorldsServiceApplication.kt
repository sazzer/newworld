package uk.co.grahamcox.worlds.worlds

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WorldsServiceApplication

fun main(args: Array<String>) {
    runApplication<WorldsServiceApplication>(*args)
}
