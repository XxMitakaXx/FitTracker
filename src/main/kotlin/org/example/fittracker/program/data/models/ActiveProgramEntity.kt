package org.example.fittracker.program.data.models

import jakarta.persistence.*
import org.example.fittracker.program.data.models.enums.TrainingLevel
import org.example.fittracker.user.data.models.UserEntity
import org.example.fittracker.program.data.models.enums.WeekDay
import java.util.*

@Entity
@Table(name = "active_programs")
data class ActiveProgramEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @OneToOne(mappedBy = "activeProgramEntity")
    val user: UserEntity,

    @Column
    val name: String,

    @OneToMany(mappedBy = "activeProgram")
    val trainingWeekEntities: List<TrainingWeekEntity> = emptyList(),

    @Column
    @Enumerated(value = EnumType.STRING)
    val trainingLevel: TrainingLevel,

    @Column
    val frequency: Int,

    @Column
    @Enumerated(value = EnumType.STRING)
    val recommendedDays: List<WeekDay>,

    @Column
    val totalCountUsed: Int,

    @OneToOne
    @JoinColumn(name = "active_program_id", referencedColumnName = "id")
    val userProgressEntity: UserProgressEntity
)
