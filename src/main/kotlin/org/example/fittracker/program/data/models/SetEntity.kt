package org.example.fittracker.program.data.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "sets")
data class SetEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column
    val kilograms: Double = 0.0,

    @Column
    val reps: Int = 0,

    @Column
    val number: Int = 1,

    @ManyToOne
    @JoinColumn(name = "exercise")
    val exercise: ExerciseEntity? = null
)