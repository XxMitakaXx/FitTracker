package org.example.fittracker.program.data

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.example.fittracker.exercise.data.ExcerciseEntity
import org.example.fittracker.user.data.UserEntity
import java.util.UUID

@Entity
@Table(name = "programs")
data class ProgramEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private val id: UUID? = null,

    @Column
    private val name: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    private val user: UserEntity,

    @OneToMany(mappedBy = "program")
    val exercises: List<ExcerciseEntity>
)
