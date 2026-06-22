package org.example.fittracker.exercise.data

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.example.fittracker.program.data.ProgramEntity
import java.util.UUID

@Entity
@Table(name = "exercises")
data class ExcerciseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private val id: UUID? = null,

    @Column
    private val name: String,

    @Column(name = "picture_url")
    private val pictureUrl: String,

    @ManyToOne
    @JoinColumn(name = "program_id")
    private val program: ProgramEntity,
)
