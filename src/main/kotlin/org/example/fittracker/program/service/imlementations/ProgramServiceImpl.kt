package org.example.fittracker.program.service.imlementations

import org.example.fittracker.program.data.repositories.ProgramRepository
import org.example.fittracker.program.service.ProgramService
import org.example.fittracker.program.service.dto.CreateProgramDTO
import org.example.fittracker.program.service.dto.ProgramDTO
import org.example.fittracker.program.service.util.toProgramDTO
import org.example.fittracker.program.service.util.toProgramEntity
import org.example.fittracker.user.data.models.UserEntity
import org.example.fittracker.user.service.UserService
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProgramServiceImpl(
    private val programRepository: ProgramRepository,
    private val userService: UserService
): ProgramService {
    override fun createProgram(createProgramDTO: CreateProgramDTO) {
        val creatorOptional: Optional<UserEntity> = userService.findUserEntityByEmail(email = createProgramDTO.email)

        if (creatorOptional.isPresent) {
           val creator = creatorOptional.get()

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

    override fun getProgramsByCreatorId(id: UUID): List<ProgramDTO> {
        val optional = programRepository.findProgramsByUsersId(id = id)

        if (optional.isPresent) {
            return optional.get().map { it.toProgramDTO() }
        }

        return emptyList()
    }
}