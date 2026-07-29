package org.example.fittracker.user.service.util

import org.example.fittracker.user.data.models.ProgressPhotoEntity
import org.example.fittracker.user.data.models.ProgressBodyWeightEntity
import org.example.fittracker.user.data.models.UserStatsEntity
import org.example.fittracker.user.data.models.enums.Gender
import org.example.fittracker.user.service.dtos.ProgressPhotoDTO
import org.example.fittracker.user.service.dtos.ProgressBodyWeightDTO
import org.example.fittracker.user.service.dtos.UserStatsDTO
import java.time.LocalDate
import java.util.Date
import java.util.UUID

fun UserStatsEntity.toUserStatsDTO(): UserStatsDTO {
    return UserStatsDTO(
        id = this.id.toString(),
        weightKg = this.weight,
        heightCm = this.height,
        gender = this.gender.toString(),
        age = this.age,
        lifetimeWorkouts = this.lifetimeWorkouts,
        lifetimeLiftedKg = this.lifetimeLiftedKg,
        lifetimeTrainingHours = this.lifetimeTrainingHours,
        lifetimePRs = this.lifetimePRs,
        progressPhotosDTOs = this.progressPhotoEntities.map { it.toProgressPhotoDTO() },
        progressBodyWeightDTOS = progressWeightEntities.map { progressWeightEntity -> progressWeightEntity.toProgressWeightDTO() }
    )
}

fun UserStatsDTO.toUserStatsEntity(): UserStatsEntity {
    return UserStatsEntity(
        weight = this.weightKg,
        height = this.heightCm,
        gender = Gender.valueOf(this.gender),
        age = this.age,
        lifetimeWorkouts = this.lifetimeWorkouts,
        lifetimeLiftedKg = this.lifetimeLiftedKg,
        lifetimeTrainingHours = this.lifetimeTrainingHours,
        lifetimePRs = this.lifetimePRs,
        progressPhotoEntities = this.progressPhotosDTOs.map { it.toProgressPhotoEntity() },
        progressWeightEntities = this.progressBodyWeightDTOS.map { it.toProgressWeightEntity() }
    )
}

fun ProgressBodyWeightEntity.toProgressWeightDTO(): ProgressBodyWeightDTO {
    return ProgressBodyWeightDTO(
        id = this.id.toString(),
        weight = weight,
        recordedAt = this.recordAt.toString(),
    )
}

fun ProgressBodyWeightDTO.toProgressWeightEntity(): ProgressBodyWeightEntity {
    return ProgressBodyWeightEntity(
        id = if (this.id != null) UUID.fromString(this.id) else null,
        weight = this.weight,
        recordAt = LocalDate.parse(this.recordedAt)
    )
}

private fun ProgressPhotoDTO.toProgressPhotoEntity(): ProgressPhotoEntity {
    return ProgressPhotoEntity(
        photoUrl = this.photoUrl,
        createdAt = Date(this.createdAt),
    )
}

private fun ProgressPhotoEntity.toProgressPhotoDTO(): ProgressPhotoDTO {
    return ProgressPhotoDTO(
        photoUrl = this.photoUrl,
        createdAt = this.createdAt.toString()
    )
}