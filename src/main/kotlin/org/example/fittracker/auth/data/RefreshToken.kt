package org.example.fittracker.auth.data

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.example.fittracker.user.data.UserEntity
import java.util.UUID
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Entity
@Table(name = "refresh_tokens")
data class RefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private val tokenUUID: UUID? = null,

    @Column
    private val userUUID: UUID,

    @Column
    val hashedToken: String,

    @Column
    val expiresAt: java.time.Instant,

    @Column
    val createdAt: Instant = Clock.System.now(),
)
