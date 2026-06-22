package org.example.fittracker.user.service.impl

import org.example.fittracker.program.data.ProgramEntity
import org.example.fittracker.user.data.UserEntity
import org.example.fittracker.user.data.UserRepository
import org.example.fittracker.user.service.UserService
import org.example.fittracker.user.service.dtos.UserDataDTO
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.collections.emptyList

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {
    override fun createUser(userData: UserDataDTO) {
        if (userData.email.isBlank() || userData.password.isBlank() || userData.username.isBlank()) {
            throw IllegalArgumentException("Invalid data format")
        }

        val userEntity = UserEntity(
            id = UUID.randomUUID(),
            email = userData.email,
            hashedPassword = userData.password,
            username = userData.username,
            programs = mutableListOf()
        )

        userRepository.save(userEntity)
    }

    override fun findUserByEmail(email: String): UserDataDTO? {
        TODO("Not yet implemented")
    }

}