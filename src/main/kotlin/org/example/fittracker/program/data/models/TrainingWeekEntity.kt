package org.example.fittracker.program.data.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "weeks")
data class TrainingWeekEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column
    val number: Int,

    @OneToMany(mappedBy = "trainingWeekEntity")
    val dayEntities: List<TrainingDayEntity> = emptyList(),

    @ManyToOne
    @JoinColumn(name = "program_id")
    val program: ProgramEntity? = null,

    @ManyToOne
    @JoinColumn(name = "active_program_id")
    val activeProgram: ActiveProgramEntity? = null,
)
