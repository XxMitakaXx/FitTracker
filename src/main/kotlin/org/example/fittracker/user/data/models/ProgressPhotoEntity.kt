package org.example.fittracker.user.data.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "progress_photo")
data class ProgressPhotoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column
    val photoUrl: String,

    @Column
    val createdAt: Date = Date(),

    @ManyToOne
    @JoinColumn(name = "user_stats_id")
    val userStatsEntity: UserStatsEntity? = null
)
