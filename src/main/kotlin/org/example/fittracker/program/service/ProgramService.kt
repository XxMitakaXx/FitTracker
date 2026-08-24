package org.example.fittracker.program.service

import org.example.fittracker.program.service.dto.CreateProgramDTO
import org.example.fittracker.program.service.dto.ProgramDTO
import java.util.*

interface ProgramService {
    fun createProgram(createProgramDTO: CreateProgramDTO)
    fun getProgramsByCreatorId(): List<ProgramDTO>
    fun deleteProgram(programDTO: ProgramDTO)
    fun getCratedPrograms(): List<ProgramDTO>
}