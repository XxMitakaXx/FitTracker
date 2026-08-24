package org.example.fittracker.user.data.models

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.example.fittracker.user.data.models.enums.Gender
import java.util.UUID

@Entity
@Table(name = "user_stats")
data class UserStatsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @OneToOne(mappedBy = "userStatsEntity", cascade = [CascadeType.ALL])
    val user: UserEntity? = null,

    @Column
    val height: Int = 0,

    @Column
    @Enumerated(EnumType.STRING)
    val gender: Gender = Gender.NON_SPECIFIED,

    @Column
    val age: Int = 0,

    @Column
    val lifetimeWorkouts: Int = 0,

    @Column
    val lifetimeLiftedKg: Double = 0.0,

    @Column
    val lifetimeTrainingHours: Int = 0,

    @Column
    val lifetimePRs: Int = 0,

    @OneToMany(mappedBy = "userStatsEntity", cascade = [CascadeType.ALL])
    val progressPhotoEntities: List<ProgressPhotoEntity> = emptyList(),

    @OneToMany(mappedBy = "userStatsEntity", cascade = [CascadeType.ALL])
    val progressBodyWeightEntities: List<ProgressBodyWeightEntity> = emptyList()


) {
    override fun toString(): String {
        return "UserStatsEntity(id=$id)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserStatsEntity

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
