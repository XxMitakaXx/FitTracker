package org.example.fittracker.program.data.repositories

import org.example.fittracker.program.data.models.ActiveProgramEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ActiveProgramRepository: JpaRepository<ActiveProgramEntity, UUID> {
}