package org.example.fittracker.program.service.dto

data class TrainingWeekDTO(
    val trainingWeekId: String,
    val number: Int,
    val trainingDays: List<TrainingDayDTO>
)
