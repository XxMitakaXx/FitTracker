package org.example.fittracker.program.service.dto

data class ReviewDTO(
    val id: String,
    val userFullName: String,
    val stars: Int,
    val strengthGainRate: String,
    val muscleGainRate: String,
    val anyModification: String,
    val review: String
)
