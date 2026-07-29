package org.example.fittracker.program.service.dto

data class ActiveProgramDTO(
    val id: String,
    val name: String,
    val trainingWeeksDTOs: List<TrainingWeekDTO>,
    val trainingLevel: String,
    val frequency: Int,
    val recommendedDays: List<String>,
    val totalCountUsed: Int,
    val userProgressDTO: UserProgressDTO
)
