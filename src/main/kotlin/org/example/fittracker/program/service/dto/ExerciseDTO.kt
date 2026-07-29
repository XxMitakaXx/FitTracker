package org.example.fittracker.program.service.dto

data class ExerciseDTO(
    val exerciseId: String,
    val name: String,
    val pictureUrl: String,
    val sets: List<SetDTO>
)