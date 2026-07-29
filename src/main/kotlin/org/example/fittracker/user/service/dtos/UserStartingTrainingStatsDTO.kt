package org.example.fittracker.user.service.dtos

data class UserStartingTrainingStatsDTO(
    val weight: Int,
    val height: Int,
    val age: Int,
    val gender: String
)
