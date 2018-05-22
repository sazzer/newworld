package uk.co.grahamcox.worlds.service.model

/**
 * Representation of some persisted resource
 */
data class Resource<out ID : Id, out DATA>(
        val identity : Identity<ID>,
        val data: DATA
)
