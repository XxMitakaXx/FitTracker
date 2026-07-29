package org.example.fittracker.user.service.dtos

import org.example.fittracker.user.data.models.enums.Gender

data class UserStatsDTO(
    val id: String,
    val weightKg: Int = 0,
    val heightCm: Int = 0,
    val gender: String = Gender.NON_SPECIFIED.toString(),
    val age: Int = 0,
    val lifetimeWorkouts: Int = 0,
    val lifetimeLiftedKg: Double = 0.0,
    val lifetimeTrainingHours: Int = 0,
    val lifetimePRs: Int = 0,
    val progressPhotosDTOs: List<ProgressPhotoDTO> = emptyList(),
    val progressBodyWeightDTOS: List<ProgressBodyWeightDTO> = emptyList()
)