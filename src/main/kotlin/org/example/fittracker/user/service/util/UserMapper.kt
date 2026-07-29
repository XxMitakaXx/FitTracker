package org.example.fittracker.user.service.util

import org.example.fittracker.program.service.util.toActiveProgramDTO
import org.example.fittracker.user.data.models.UserEntity
import org.example.fittracker.user.service.dtos.UserDataDTO
import org.example.fittracker.user.service.dtos.UserTrainingDataDTO


fun UserEntity.toUserTrainingDataDTO(): UserTrainingDataDTO {
    return UserTrainingDataDTO(
        activeProgramDTO = this.activeProgramEntity?.toActiveProgramDTO(),
        userStatsDTO = this.userStatsEntity?.toUserStatsDTO()
    )
}

fun UserEntity.toUserDataDTO(): UserDataDTO {
    return UserDataDTO(
        email = email,
        password = hashedPassword,
        firstName = firstName,
        lastName = lastName
    )
}