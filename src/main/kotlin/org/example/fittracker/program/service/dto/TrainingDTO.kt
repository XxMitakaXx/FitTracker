package org.example.fittracker.program.service.dto

data class TrainingDTO(
    val trainingId: String,
    val exercises: List<ExerciseDTO>
)