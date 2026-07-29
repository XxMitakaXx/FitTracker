package org.example.fittracker.user.service

import org.example.fittracker.user.data.models.UserEntity
import org.example.fittracker.user.service.dtos.ProgressBodyWeightDTO
import org.example.fittracker.user.service.dtos.UserDataDTO
import org.example.fittracker.user.service.dtos.UserStartingTrainingStatsDTO
import org.example.fittracker.user.service.dtos.UserStatsDTO
import org.example.fittracker.user.service.dtos.UserTrainingDataDTO
import java.util.Optional


interface UserService {
    fun createUser(userData: UserDataDTO)
    fun findUserByEmail(email: String): UserDataDTO?
    fun findUserEntityByEmail(email: String): Optional<UserEntity>
    fun findUserTrainDataById(): UserTrainingDataDTO
    fun editUser(user: UserEntity)
    fun saveUserStats(userStatsDTO: UserStatsDTO)
    fun saveUserStartingTrainingData(userStartingTrainingStatsDTO: UserStartingTrainingStatsDTO)
    fun saveProgressWeight(progressBodyWeightDTO: ProgressBodyWeightDTO)
}