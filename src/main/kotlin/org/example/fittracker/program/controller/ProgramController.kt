package org.example.fittracker.program.controller

import org.example.fittracker.program.service.ProgramService
import org.example.fittracker.program.service.dto.CreateProgramDTO
import org.example.fittracker.program.service.dto.ProgramDTO
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping(value = ["/programs"])
class ProgramController(
    private val programService: ProgramService
) {
    @GetMapping
    fun programs(): List<ProgramDTO> {
        val creatorId = SecurityContextHolder.getContext().authentication?.principal as String
        val formattedCreatorId = "${creatorId.substring(startIndex = 0, endIndex = 8)}-${creatorId.substring(startIndex = 8, endIndex = 12)}-${creatorId.substring(startIndex = 12, endIndex = 16)}-${creatorId.substring(startIndex = 16, endIndex = 20)}-${creatorId.substring(startIndex = 20)}"
        val uUID = UUID.fromString(formattedCreatorId)
        return this.programService.getProgramsByCreatorId(id = uUID)
    }

    @PostMapping(value = ["/craate_training"])
    fun createTraining(@RequestBody createProgramDTO: CreateProgramDTO) {
        this.programService.createProgram(createProgramDTO = createProgramDTO)
    }
}
