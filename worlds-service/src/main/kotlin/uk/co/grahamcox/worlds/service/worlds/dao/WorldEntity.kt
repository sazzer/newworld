package uk.co.grahamcox.worlds.service.worlds.dao

import java.time.Instant
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * JPA Entity to represent a World record
 */
@Entity
@Table(name = "worlds")
data class WorldEntity(
        @Id
        @Column(name = "world_id")
        val id: UUID,

        var version: UUID,

        val created: Instant,

        var updated: Instant,

        var name: String,

        @Column(name = "display_name")
        var displayName: String,

        var description: String,

        var owner: UUID
)
