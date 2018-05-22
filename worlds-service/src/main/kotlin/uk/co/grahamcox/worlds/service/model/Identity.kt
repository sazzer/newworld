package uk.co.grahamcox.worlds.service.model

import java.time.Instant

/**
 * The identity of some resource
 */
data class Identity<out ID : Id>(
        val id: ID,
        val version: String,
        val created: Instant,
        val updated: Instant
)
