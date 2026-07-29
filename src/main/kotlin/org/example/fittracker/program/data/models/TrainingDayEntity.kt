package org.example.fittracker.program.data.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.example.fittracker.program.data.models.enums.WeekDay
import java.util.UUID

@Entity
@Table(name = "days")
data class TrainingDayEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Enumerated(EnumType.STRING)
    @Column
    val weekDay: WeekDay,

    @ManyToOne
    @JoinColumn(name = "week_id")
    val trainingWeekEntity: TrainingWeekEntity? = null,

    @OneToMany(mappedBy = "trainingDay")
    val trainings: List<TrainingEntity> = emptyList()
)
