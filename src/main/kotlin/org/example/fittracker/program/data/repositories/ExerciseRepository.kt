package org.example.fittracker.program.data.repositories

import org.example.fittracker.program.data.models.ExerciseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface ExerciseRepository: JpaRepository<ExerciseEntity, UUID> {
    fun findByName(name: String): Optional<ExerciseEntity>
}