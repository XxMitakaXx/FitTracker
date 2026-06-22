package org.example.fittracker.exercise.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface ExerciseRepository: JpaRepository<ExcerciseEntity, UUID> {
    fun findByName(name: String): Optional<ExcerciseEntity>
}