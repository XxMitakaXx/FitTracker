package org.example.fittracker.user.service.dtos

import org.example.fittracker.program.service.dto.ActiveProgramDTO

data class UserTrainingDataDTO(
    val activeProgramDTO: ActiveProgramDTO? = null,
    val userStatsDTO: UserStatsDTO? = null
)
