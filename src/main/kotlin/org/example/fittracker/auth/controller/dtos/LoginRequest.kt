package org.example.fittracker.auth.controller.dtos

data class LoginRequest(
    val email: String,
    val password: String
)
