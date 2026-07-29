package org.example.fittracker.program.data.repositories

import org.example.fittracker.program.data.models.TrainingWeekEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TrainingWeekRepository: JpaRepository<TrainingWeekEntity, UUID> {
}