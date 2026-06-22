package org.example.fittracker.auth.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface RefreshTokenRepository: JpaRepository<RefreshToken, UUID> {
    fun findRefreshTokensByUserUUIDAndHashedToken(userId: UUID, hashedToken: String): Optional<RefreshToken>
    fun deleteByUserUUIDAndHashedToken(userId: UUID, hashedToken: String)
}