package org.example.fittracker.user.data.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "user_progress_weight")
data class ProgressBodyWeightEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column
    val weight: Int,

    @Column
    val recordAt: LocalDate,

    @ManyToOne
    @JoinColumn(name = "user_stats_id")
    val userStatsEntity: UserStatsEntity? = null
)