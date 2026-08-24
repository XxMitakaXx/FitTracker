package org.example.fittracker.user.service.impl

import jakarta.transaction.Transactional
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
import org.example.fittracker.user.service.util.toProgressBodyWeightEntity
import org.example.fittracker.user.service.util.toUserDataDTO
import org.example.fittracker.user.service.util.toUserStatsEntity
import org.example.fittracker.user.service.util.toUserTrainingDataDTO
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter
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
        if (optional.get() != null) {
            val userEntity = optional.get()

            return userEntity.toUserDataDTO()
        }

        throw IllegalArgumentException("Invalid data format")
    }

    override fun findUserEntityByEmail(email: String): Optional<UserEntity> {
        return userRepository.findByEmail(email = email)
    }

    override fun findUserTrainDataById(): UserTrainingDataDTO {
        val userEntity = findUser()

        if (userEntity != null) {
            return userEntity.toUserTrainingDataDTO()
        }

        throw IllegalArgumentException("Invalid data format")
    }

    override fun editUser(user: UserEntity) {
        userRepository.save(user = user)
    }

    override fun saveUserStats(userStatsDTO: UserStatsDTO) {
        userStatsRepository.save(userStatsDTO.toUserStatsEntity())
    }

    override fun saveUserStartingTrainingData(userStartingTrainingStatsDTO: UserStartingTrainingStatsDTO) {
        val userEntity = findUser()
        if (userEntity != null) {
            val userStatsEntity: UserStatsEntity? = userEntity.userStatsEntity
            var updatedUserStatsEntity: UserStatsEntity = userStatsEntity?.copy(
                height = userStartingTrainingStatsDTO.height,
                age = userStartingTrainingStatsDTO.age,
                gender = Gender.valueOf(userStartingTrainingStatsDTO.gender)
            )
                ?: UserStatsEntity(
                    height = userStartingTrainingStatsDTO.height,
                    age = userStartingTrainingStatsDTO.age,
                    gender = Gender.valueOf(userStartingTrainingStatsDTO.gender)
                )

            updatedUserStatsEntity = updatedUserStatsEntity.copy(
                user = userEntity
            )

            userStatsRepository.save(updatedUserStatsEntity)

            val updatedUser = userEntity.copy(userStatsEntity = updatedUserStatsEntity)

            userRepository.save(user = updatedUser)
        }
    }

    @Transactional
    override fun saveProgressWeight(progressBodyWeightDTO: ProgressBodyWeightDTO) {
        val userEntity = findUser()
        if (userEntity != null) {
            val userStatsEntity = userEntity.userStatsEntity
            if (userStatsEntity != null) {
                var progressBodyWeightEntity = progressBodyWeightDTO.toProgressBodyWeightEntity()
                progressBodyWeightEntity = progressBodyWeightEntity.copy(
                    userStatsEntity = userStatsEntity
                )

                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val existingEntity = userStatsEntity.progressBodyWeightEntities.find { progressBodyWeightEntityItem ->
                    progressBodyWeightEntityItem.recordAt.format(formatter) == progressBodyWeightEntity.recordAt.format(formatter)
                }

                if (existingEntity != null) {
                    val updatedProgressBodyWeightEntities = userStatsEntity.progressBodyWeightEntities.minus(element = existingEntity).plus(element = progressBodyWeightEntity)
                    val updatedUserStatsEntity = userStatsEntity.copy(
                        progressBodyWeightEntities = updatedProgressBodyWeightEntities
                    )

                    val existingEntityId = existingEntity.id
                    if (existingEntityId != null) {
                        progressWeightRepository.deleteById(existingEntityId)
                    }
                    progressWeightRepository.save(progressBodyWeightEntity)
                    userStatsRepository.save(updatedUserStatsEntity)
                } else {
                    userStatsEntity.progressBodyWeightEntities.plus(element = progressBodyWeightEntity)

                    progressWeightRepository.save(progressBodyWeightEntity)
                    userStatsRepository.save(userStatsEntity)
                }
            }
        }
    }

    override fun deleteProgressWeight(progressBodyWeightDTO: ProgressBodyWeightDTO) {
        val userEntity = findUser()
        if (userEntity != null) {
            val userStatsEntity = userEntity.userStatsEntity

            if (userStatsEntity != null) {
                val progressBodyWeightEntity = progressBodyWeightDTO.toProgressBodyWeightEntity()
                progressWeightRepository.delete(progressBodyWeightEntity)


                val updatedUserStatsEntity = userStatsEntity.copy(
                    progressBodyWeightEntities = userStatsEntity.progressBodyWeightEntities.minus(element = progressBodyWeightEntity)
                )

                userStatsRepository.save(updatedUserStatsEntity)

                val updatedUserEntity = userEntity.copy(
                    userStatsEntity = updatedUserStatsEntity
                )

                userRepository.save(updatedUserEntity)
            }
        }
    }

     override fun findUser(): UserEntity? {
        val userId = SecurityContextHolder.getContext().authentication?.principal as String
        val formattedCreatorId = "${userId.substring(startIndex = 0, endIndex = 8)}-${userId.substring(startIndex = 8, endIndex = 12)}-${userId.substring(startIndex = 12, endIndex = 16)}-${userId.substring(startIndex = 16, endIndex = 20)}-${userId.substring(startIndex = 20)}"
        val uuid = UUID.fromString(formattedCreatorId)

        val optional = this.userRepository.findById(uuid)

        return if (optional.isPresent) {
            optional.get()
        } else {
            null
        }
    }
}