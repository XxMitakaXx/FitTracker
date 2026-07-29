package org.example.fittracker.program.service.dto

data class TrainingDayDTO(
    val trainingDayId: String,
    val weekDay: String,
    val trainings: List<TrainingDTO>
)
