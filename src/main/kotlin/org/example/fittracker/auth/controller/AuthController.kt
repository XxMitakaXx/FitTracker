package org.example.fittracker.auth.controller

import org.example.fittracker.auth.controller.dtos.LoginRequest
import org.example.fittracker.auth.controller.dtos.RegisterRequest
import org.example.fittracker.auth.controller.dtos.LogoutRequest
import org.example.fittracker.auth.controller.dtos.RefreshRequest
import org.example.fittracker.auth.data.TokenPair
import org.example.fittracker.auth.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/register")
    fun register(
        @RequestBody body: RegisterRequest
    ) {
        authService.register(
            email = body.email,
            password =  body.password,
            firstName =  body.firstName,
            lastName = body.lastName
        )
    }

    @PostMapping("/login")
    fun login(
        @RequestBody body: LoginRequest
    ): TokenPair {
        return authService.login(email = body.email, password =  body.password)
    }

    @PostMapping("/refresh")
    fun refresh(
        @RequestBody body: RefreshRequest
    ): TokenPair {
        return authService.refresh(refreshToken = body.refreshToken)
    }

    @PostMapping("/logout")
    fun logout(@RequestBody logoutRequest: LogoutRequest) {
        authService.logout(refreshToken = logoutRequest.refreshToken)
    }
}