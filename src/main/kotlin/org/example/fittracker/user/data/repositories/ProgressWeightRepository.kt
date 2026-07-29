package org.example.fittracker.user.data.repositories

import org.example.fittracker.user.data.models.ProgressBodyWeightEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ProgressWeightRepository: JpaRepository<ProgressBodyWeightEntity, UUID> {
}