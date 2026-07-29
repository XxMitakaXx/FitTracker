package org.example.fittracker.program.service.dto


data class CreateProgramDTO(
    val name: String,
    val email: String,
    val trainingLevel: String,
    val trainingType: String,
    val daysPerWeek: String,
    val recommendedDays: List<String>,
    val timePerWorkoutMinutes: Int,
    val trainingWeeks: List<TrainingWeekDTO>,
    val equipment: String
)
