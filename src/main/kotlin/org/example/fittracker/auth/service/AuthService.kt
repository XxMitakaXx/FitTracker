package org.example.fittracker.auth.service

import jakarta.transaction.Transactional
import org.example.fittracker.auth.data.RefreshToken
import org.example.fittracker.auth.data.RefreshTokenRepository
import org.example.fittracker.auth.data.TokenPair
import org.example.fittracker.auth.util.HashEncoder
import org.example.fittracker.auth.util.toHexString
import org.example.fittracker.user.data.UserEntity
import org.example.fittracker.user.data.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.security.MessageDigest
import java.time.Instant
import java.util.*
import kotlin.time.ExperimentalTime

@Service
class AuthService(
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val hashEncoder: HashEncoder,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    fun register(email: String, password: String, username: String) {
        val user = userRepository.findByEmail(email)
        if (user.isPresent) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "A user with that email already exists.")
        }

        userRepository.save(
            UserEntity(
                email = email.trim(),
                hashedPassword = hashEncoder.encode(password),
                username = username.trim()
            )
        )
    }

    fun login(email: String, password: String): TokenPair {
        val optional = userRepository.findByEmail(email)
        if (optional.isEmpty) {
            throw IllegalArgumentException("Email does not exist.")
        }

        val user = optional.get()

        if (!hashEncoder.matches(password, user.hashedPassword)) {
            throw BadCredentialsException("Invalid credentials.")
        }

        val userUUID = user.id
        if (userUUID != null) {
            val newAccessToken = jwtService.generateAccessToken(userUUID.toHexString())
            val newRefreshToken = jwtService.generateRefreshToken(userUUID.toHexString())

            storeRefreshToken(userUUID, newRefreshToken)

            return TokenPair(
                accessToken = newAccessToken,
                refreshToken = newRefreshToken
            )
        }

        throw BadCredentialsException("Invalid credentials.")
    }

    @Transactional
    fun refresh(refreshToken: String): TokenPair {
        if (!jwtService.validateRefreshToken(refreshToken)) {
            throw ResponseStatusException(HttpStatusCode.valueOf(401), "Invalid refresh token.")
        }

        val userId = jwtService.getUserIdFromToken(refreshToken)
        val user = userRepository.findById(UUID.fromString(userId)).orElseThrow {
            ResponseStatusException(HttpStatusCode.valueOf(401), "Invalid refresh token.")
        }

        val userUUID = user.id
        if (userUUID != null) {
            val hashed = hashToken(refreshToken)
            refreshTokenRepository.findRefreshTokensByUserUUIDAndHashedToken(userUUID, hashed)
                ?: ResponseStatusException(HttpStatusCode.valueOf(401), "Refresh token is not recognized (maybe used or expired).")

            refreshTokenRepository.deleteByUserUUIDAndHashedToken(userUUID, hashed)

            val newAccessToken = jwtService.generateAccessToken(userId)
            val newRefreshToken = jwtService.generateRefreshToken(userId)

            storeRefreshToken(userUUID, newRefreshToken)

            return TokenPair(
                accessToken = newAccessToken,
                refreshToken = newRefreshToken
            )
        }

        throw BadCredentialsException("Invalid credentials.")
    }

    fun logout(refreshToken: String) {
        if (!jwtService.validateRefreshToken(refreshToken)) {
            throw ResponseStatusException(HttpStatusCode.valueOf(401), "Invalid refresh token.")
        }

        val userId = jwtService.getUserIdFromToken(refreshToken)
        val user = userRepository.findById(UUID.fromString(userId)).orElseThrow {
            ResponseStatusException(HttpStatusCode.valueOf(401), "Invalid refresh token.")
        }

        val userUUID = user.id
        if (userUUID != null) {
            val hashed = hashToken(refreshToken)
            refreshTokenRepository.findRefreshTokensByUserUUIDAndHashedToken(userUUID, hashed)

            refreshTokenRepository.deleteByUserUUIDAndHashedToken(userUUID, hashed)
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun storeRefreshToken(userId: UUID, rawRefreshToken: String) {
        val hashed = hashToken(rawRefreshToken)
        val expiryMs = jwtService.refreshTokenValidityMs
        val expiresAt = Instant.now().plusMillis(expiryMs)

        refreshTokenRepository.save(
            RefreshToken(
                userUUID = userId,
                expiresAt = expiresAt,
                hashedToken = hashed
            )
        )
    }

    private fun hashToken(token: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(token.encodeToByteArray())
        return Base64.getEncoder().encodeToString(hashBytes)
    }
}