package org.example.fittracker.auth.controller.dtos

data class RegisterRequest(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String
)