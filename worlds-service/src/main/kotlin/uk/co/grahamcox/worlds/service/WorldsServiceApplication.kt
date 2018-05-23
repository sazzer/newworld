package uk.co.grahamcox.worlds.service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.time.Clock

@SpringBootApplication
@EnableJpaRepositories
class WorldsServiceApplication {
    /** The clock to use */
    @Bean
    fun clock() = Clock.systemUTC()
}

fun main(args: Array<String>) {
    runApplication<WorldsServiceApplication>(*args)
}
