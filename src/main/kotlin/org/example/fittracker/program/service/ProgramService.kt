package org.example.fittracker.program.service

import org.example.fittracker.program.service.dto.CreateProgramDTO
import org.example.fittracker.program.service.dto.ProgramDTO
import java.util.*

interface ProgramService {
    fun createProgram(createProgramDTO: CreateProgramDTO)
    fun getProgramsByCreatorId(id: UUID): List<ProgramDTO>
}