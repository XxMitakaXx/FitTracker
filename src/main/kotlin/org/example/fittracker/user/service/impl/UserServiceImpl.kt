package org.example.fittracker.user.service.impl

import org.example.fittracker.user.data.models.UserEntity
import org.example.fittracker.user.data.models.UserStatsEntity
import org.example.fittracker.user.data.models.enums.Gender
import org.example.fittracker.user.data.repositories.ProgressWeightRepository
import org.example.fittracker.user.data.repositories.UserRepository
import org.example.fittracker.user.data.repositories.UserStatsRepository
import org.example.fittracker.user.service.UserService
import org.example.fittracker.user.service.dtos.ProgressBodyWeightDTO
import org.example.fittracker.user.service.dtos.UserDataDTO
import org.example.fittracker.user.service.dtos.UserStartingTrainingStatsDTO
import org.example.fittracker.user.service.dtos.UserStatsDTO
import org.example.fittracker.user.service.dtos.UserTrainingDataDTO
import org.example.fittracker.user.service.util.toProgressWeightEntity
import org.example.fittracker.user.service.util.toUserDataDTO
import org.example.fittracker.user.service.util.toUserStatsEntity
import org.example.fittracker.user.service.util.toUserTrainingDataDTO
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userStatsRepository: UserStatsRepository,
    private val progressWeightRepository: ProgressWeightRepository
): UserService {
    override fun createUser(userData: UserDataDTO) {
        if (userData.email.isBlank() || userData.password.isBlank() || userData.firstName.isBlank() || userData.lastName.isBlank()) {
            throw IllegalArgumentException("Invalid data format")
        }

        val userEntity = UserEntity(
            id = UUID.randomUUID(),
            email = userData.email,
            hashedPassword = userData.password,
            firstName = userData.firstName,
            lastName = userData.lastName
        )

        userRepository.save(userEntity)
    }

    override fun findUserByEmail(email: String): UserDataDTO? {
        val optional = userRepository.findByEmail(email = email)
        if (optional != null) {
            val userEntity = optional.get()

            return userEntity.toUserDataDTO()
        }

        throw IllegalArgumentException("Invalid data format")
    }

    override fun findUserEntityByEmail(email: String): Optional<UserEntity> {
        return userRepository.findByEmail(email = email)
    }

    override fun findUserTrainDataById(): UserTrainingDataDTO {
        val userId = SecurityContextHolder.getContext().authentication?.principal as String
        val formattedCreatorId = "${userId.substring(startIndex = 0, endIndex = 8)}-${userId.substring(startIndex = 8, endIndex = 12)}-${userId.substring(startIndex = 12, endIndex = 16)}-${userId.substring(startIndex = 16, endIndex = 20)}-${userId.substring(startIndex = 20)}"
        val uUID = UUID.fromString(formattedCreatorId)

        val optional = this.userRepository.findById(uUID)

        if (optional.isPresent) {
            return optional.get().toUserTrainingDataDTO()
        }

        throw IllegalArgumentException("Invalid data format")
    }

    override fun editUser(user: UserEntity) {
        userRepository.save(user = user)
    }

    override fun saveUserStats(userStatsDTO: UserStatsDTO) {
        userStatsRepository.save(userStatsEntity = userStatsDTO.toUserStatsEntity())
    }

    override fun saveUserStartingTrainingData(userStartingTrainingStatsDTO: UserStartingTrainingStatsDTO) {
        val userId = SecurityContextHolder.getContext().authentication?.principal as String
        val formattedCreatorId = "${userId.substring(startIndex = 0, endIndex = 8)}-${userId.substring(startIndex = 8, endIndex = 12)}-${userId.substring(startIndex = 12, endIndex = 16)}-${userId.substring(startIndex = 16, endIndex = 20)}-${userId.substring(startIndex = 20)}"
        val uUID = UUID.fromString(formattedCreatorId)

        val optional = this.userRepository.findById(uUID)

        if (optional.isPresent) {
            val userEntity = optional.get()


            var userStatsEntity: UserStatsEntity? = userEntity.userStatsEntity
            val updatedUserStatsEntity: UserStatsEntity
            if (userStatsEntity != null) {
                updatedUserStatsEntity = userStatsEntity.copy(
                    weight = userStartingTrainingStatsDTO.weight,
                    height = userStartingTrainingStatsDTO.height,
                    age = userStartingTrainingStatsDTO.age,
                    gender = Gender.valueOf(userStartingTrainingStatsDTO.gender)
                )
            } else {
                updatedUserStatsEntity = UserStatsEntity(
                    weight = userStartingTrainingStatsDTO.weight,
                    height = userStartingTrainingStatsDTO.height,
                    age = userStartingTrainingStatsDTO.age,
                    gender = Gender.valueOf(userStartingTrainingStatsDTO.gender)
                )
            }

            userStatsRepository.save(userStatsEntity = updatedUserStatsEntity)

            val updatedUser = userEntity.copy(userStatsEntity = updatedUserStatsEntity)

            userRepository.save(user = updatedUser)
        }
    }

    override fun saveProgressWeight(progressBodyWeightDTO: ProgressBodyWeightDTO) {
        val userId = SecurityContextHolder.getContext().authentication?.principal as String
        val formattedCreatorId = "${userId.substring(startIndex = 0, endIndex = 8)}-${userId.substring(startIndex = 8, endIndex = 12)}-${userId.substring(startIndex = 12, endIndex = 16)}-${userId.substring(startIndex = 16, endIndex = 20)}-${userId.substring(startIndex = 20)}"
        val uUID = UUID.fromString(formattedCreatorId)

        val optional = this.userRepository.findById(uUID)

        if (optional.isPresent) {
            val userEntity = optional.get()
            val userStatsEntity = userEntity.userStatsEntity
            if (userStatsEntity != null) {
                var progressWeightEntity = progressBodyWeightDTO.toProgressWeightEntity()
                progressWeightEntity = progressWeightEntity.copy(
                    userStatsEntity = userStatsEntity
                )
                progressWeightRepository.save(progressWeightEntity)

                val updatedWeightProgressEntities = userStatsEntity.progressWeightEntities.plus(element = progressBodyWeightDTO.toProgressWeightEntity())

                val updatedUserStatsEntity =  userStatsEntity.copy(
                    progressWeightEntities = updatedWeightProgressEntities
                )

                val updatedUser = userEntity.copy(userStatsEntity = updatedUserStatsEntity)

                userRepository.save(user = updatedUser)
            }
        }
    }
}