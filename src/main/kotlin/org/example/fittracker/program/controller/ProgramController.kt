package org.example.fittracker.program.controller

import org.example.fittracker.program.service.ProgramService
import org.example.fittracker.program.service.dto.CreateProgramDTO
import org.example.fittracker.program.service.dto.ProgramDTO
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/programs"])
class ProgramController(
    private val programService: ProgramService
) {
//    @GetMapping
//    fun programs(): List<ProgramDTO> {
//        return this.programService.getProgramsByCreatorId()
//    }

    @GetMapping(value = ["/created_programs"])
    fun getCreatedPrograms(): List<ProgramDTO> {
        return this.programService.getCratedPrograms()
    }

    @PostMapping(value = ["/craate_training"])
    fun createProgram(@RequestBody createProgramDTO: CreateProgramDTO) {
        this.programService.createProgram(createProgramDTO = createProgramDTO)
    }

    @DeleteMapping(value = ["/delete_program"])
    fun deleteProgram(@RequestBody programDTO: ProgramDTO) {
        this.programService.deleteProgram(programDTO = programDTO)
    }
}
