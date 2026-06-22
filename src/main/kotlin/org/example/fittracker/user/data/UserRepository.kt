package org.example.fittracker.user.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface UserRepository: JpaRepository<UserEntity, UUID> {
    fun save(user: UserEntity): UserEntity
    fun findByEmail(email: String): Optional<UserEntity>
}