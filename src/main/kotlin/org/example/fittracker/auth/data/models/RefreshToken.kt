package org.example.fittracker.auth.data.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

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
    val expiresAt: Instant,

    @Column
    val createdAt: kotlin.time.Instant = Clock.System.now(),
)