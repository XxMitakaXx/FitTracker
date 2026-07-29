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
@Table(name = "exercises")
data class ExerciseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column
    val name: String,

    @Column(name = "picture_url")
    val pictureUrl: String,

    @ManyToOne
    @JoinColumn(name = "training")
    val training: TrainingEntity? = null,

    @OneToMany(mappedBy = "exercise")
    val sets: List<SetEntity>
)