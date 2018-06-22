package uk.co.grahamcox.worlds.service.users.dao

import java.time.Instant
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * JPA Entity to represent a User record
 */
@Entity
@Table(name = "users")
data class UserEntity(
        @Id
        @Column(name = "user_id")
        val id: UUID,

        var version: UUID,

        val created: Instant,

        var updated: Instant,

        var email: String,

        var username: String,

        @Column(name = "display_name")
        var displayName: String,

        @Column(name = "password_hash")
        var passwordHash: String,

        @Column(name = "password_salt")
        var paswordSalt: String
)
