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
import jakarta.persistence.Table
import org.example.fittracker.program.data.models.enums.AnyModification
import org.example.fittracker.program.data.models.enums.MuscleGainRate
import org.example.fittracker.program.data.models.enums.StrengthGainRate
import org.example.fittracker.user.data.models.UserEntity
import java.util.UUID

@Entity
@Table(name = "reviews")
data class ReviewEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: UserEntity,

    @ManyToOne
    @JoinColumn(name = "program_id")
    val program: ProgramEntity,

    @Column
    val stars: Int,

    @Column
    @Enumerated(value = EnumType.STRING)
    val strengthGainRate: StrengthGainRate,

    @Column
    @Enumerated(value = EnumType.STRING)
    val muscleGainRate: MuscleGainRate,

    @Column
    @Enumerated(value = EnumType.STRING)
    val anyModification: AnyModification,

    @Column
    val review: String
)
