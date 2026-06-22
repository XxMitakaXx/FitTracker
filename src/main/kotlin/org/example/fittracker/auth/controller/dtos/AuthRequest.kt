package org.example.fittracker.auth.controller.dtos

data class AuthRequest(
    val email: String,
    val password: String,
    val username: String?
)