package org.example.fittracker.user.service.dtos

data class ProgressBodyWeightDTO(
    val id: String? = null,
    val weight: Int,
    val recordedAt: String
)