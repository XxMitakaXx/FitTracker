package org.example.fittracker.program.service.imlementations

import org.example.fittracker.program.data.repositories.ProgramRepository
import org.example.fittracker.program.service.ProgramService
import org.example.fittracker.program.service.dto.CreateProgramDTO
import org.example.fittracker.program.service.dto.ProgramDTO
import org.example.fittracker.program.service.util.toProgramDTO
import org.example.fittracker.program.service.util.toProgramEntity
import org.example.fittracker.user.service.UserService
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProgramServiceImpl(
    private val programRepository: ProgramRepository,
    private val userService: UserService
): ProgramService {
    override fun createProgram(createProgramDTO: CreateProgramDTO) {
        val creator = userService.findUser()

        if (creator != null) {
            val programEntity = createProgramDTO.toProgramEntity(creator = creator)
            programRepository.save(programEntity)

            val updatedUserEntity = creator.copy(
                createdPrograms = creator.createdPrograms + programEntity
            )

            userService.editUser(user = updatedUserEntity)
        } else {
            throw IllegalArgumentException("The user with this email does not exist!")
        }
    }

    override fun getProgramsByCreatorId(): List<ProgramDTO> {
        val userId = userService.findUser()?.id
        if (userId != null) {
            val optional = programRepository.findProgramsByUsersId(id = userId)

            if (optional.isPresent) {
                return optional.get().map { it.toProgramDTO() }
            }
        }

        return emptyList()
    }

    override fun deleteProgram(programDTO: ProgramDTO) {
        val programId = programDTO.id
        val formattedProgramId = "${programId.substring(startIndex = 0, endIndex = 8)}-${programId.substring(startIndex = 8, endIndex = 12)}-${programId.substring(startIndex = 12, endIndex = 16)}-${programId.substring(startIndex = 16, endIndex = 20)}-${programId.substring(startIndex = 20)}"
        val uuid = UUID.fromString(formattedProgramId)

        this.programRepository.deleteById(uuid)
    }

    override fun getCratedPrograms(): List<ProgramDTO> {
        val userEntity = userService.findUser()

        return userEntity?.createdPrograms?.map { programEntity -> programEntity.toProgramDTO() } ?: emptyList<ProgramDTO>()
    }
}