package org.example.fittracker.program.data.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.example.fittracker.program.data.models.enums.DaysPerWeek
import org.example.fittracker.program.data.models.enums.Equipment
import org.example.fittracker.program.data.models.enums.TrainingLevel
import org.example.fittracker.program.data.models.enums.TrainingType
import org.example.fittracker.user.data.models.UserEntity
import org.example.fittracker.program.data.models.enums.WeekDay
import java.util.UUID

@Entity
@Table(name = "programs")
data class ProgramEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column
    val name: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val creator: UserEntity,

    @ManyToMany(mappedBy = "savedPrograms")
    val users: List<UserEntity> = emptyList(),

    @OneToMany(mappedBy = "program")
    val weekEntities: List<TrainingWeekEntity> = emptyList(),

    @Column
    @Enumerated(value = EnumType.STRING)
    val trainingLevel: TrainingLevel,

    @Column
    @Enumerated(value = EnumType.STRING)
    val trainingType: TrainingType,

    @Column
    val daysPerWeek: DaysPerWeek,

    @Column
    @Enumerated(value = EnumType.STRING)
    val recommendedDays: List<WeekDay>,

    @Column
    val timePerWorkoutMinutes: Int,

    @Column
    val totalCountUsed: Int = 0,

    @Column
    val rate: Double? = null,

    @Column
    val equipment: Equipment,

    @OneToMany(mappedBy = "program")
    val reviewEntities: List<ReviewEntity> = emptyList(),

    @Column
    val isPublic: Boolean = false
)