package org.example.fittracker.program.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface ProgramRepository: JpaRepository<ProgramEntity, UUID> {
    fun findByName(name: String): Optional<ProgramEntity>
}