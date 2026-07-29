package org.example.fittracker.user.data.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.example.fittracker.program.data.models.ActiveProgramEntity
import org.example.fittracker.program.data.models.ProgramEntity
import org.example.fittracker.program.data.models.ReviewEntity
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column(nullable = false)
    val hashedPassword: String,

    @Column(nullable = false)
    val firstName: String,

    @Column(nullable = false)
    val lastName: String,

    @OneToMany(mappedBy = "creator")
    val createdPrograms: List<ProgramEntity> = emptyList(),

    @ManyToMany
    @JoinTable(
        name = "users_programs",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "program_id")]
    )
    val usedPrograms: List<ProgramEntity> = emptyList(),

    @OneToOne
    @JoinColumn(name = "user_entity_id", referencedColumnName = "id")
    val activeProgramEntity: ActiveProgramEntity? = null,

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val userStatsEntity: UserStatsEntity? = null,

    @OneToMany(mappedBy = "user")
    val reviewEntities: List<ReviewEntity> = emptyList(),

    @Column(nullable = false)
    val createdAt: Date = Date(),
)