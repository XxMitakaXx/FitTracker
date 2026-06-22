package org.example.fittracker.user.service

import org.example.fittracker.user.service.dtos.UserDataDTO


interface UserService {
    fun createUser(userData: UserDataDTO)
    fun findUserByEmail(email: String): UserDataDTO?
}