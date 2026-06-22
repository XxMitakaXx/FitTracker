package org.example.fittracker.auth.controller.dtos

data class LogoutRequest(
    val refreshToken: String
)