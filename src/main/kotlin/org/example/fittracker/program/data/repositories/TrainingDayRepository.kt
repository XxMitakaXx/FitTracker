package org.example.fittracker.program.data.repositories

import org.example.fittracker.program.data.models.TrainingDayEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TrainingDayRepository: JpaRepository<TrainingDayEntity, UUID> {
}