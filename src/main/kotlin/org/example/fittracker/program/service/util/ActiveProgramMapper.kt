package org.example.fittracker.program.service.util

import org.example.fittracker.program.data.models.ActiveProgramEntity
import org.example.fittracker.program.data.models.ExerciseEntity
import org.example.fittracker.program.data.models.SetEntity
import org.example.fittracker.program.data.models.TrainingDayEntity
import org.example.fittracker.program.data.models.TrainingEntity
import org.example.fittracker.program.data.models.TrainingWeekEntity
import org.example.fittracker.program.data.models.UserProgressEntity
import org.example.fittracker.program.service.dto.ActiveProgramDTO
import org.example.fittracker.program.service.dto.ExerciseDTO
import org.example.fittracker.program.service.dto.SetDTO
import org.example.fittracker.program.service.dto.TrainingDTO
import org.example.fittracker.program.service.dto.TrainingDayDTO
import org.example.fittracker.program.service.dto.TrainingWeekDTO
import org.example.fittracker.program.service.dto.UserProgressDTO

fun ActiveProgramEntity.toActiveProgramDTO(): ActiveProgramDTO {
    return ActiveProgramDTO(
        id = this.id.toString(),
        name = this.name,
        trainingWeeksDTOs = this.trainingWeekEntities.map { trainingWeekEntity -> trainingWeekEntity.toTrainingWeekDTO() },
        trainingLevel = this.trainingLevel.toString(),
        frequency = this.frequency,
        recommendedDays = this.recommendedDays.map { recommendedDay -> recommendedDay.toString() },
        totalCountUsed = this.totalCountUsed,
        userProgressDTO = this.userProgressEntity.toUserProgressDTO(),
    )
}

private fun UserProgressEntity.toUserProgressDTO(): UserProgressDTO {
    return UserProgressDTO(
        week = this.week,
        days = this.day
    )
}

private fun TrainingWeekEntity.toTrainingWeekDTO(): TrainingWeekDTO {
    return TrainingWeekDTO(
        trainingWeekId = this.id.toString(),
        number = this.number,
        trainingDays = this.dayEntities.map { trainingDayEntity -> trainingDayEntity.toTrainingDayDTO()
        }
    )
}

private fun TrainingDayEntity.toTrainingDayDTO(): TrainingDayDTO {
    return TrainingDayDTO(
        trainingDayId = this.id.toString(),
        weekDay = this.weekDay.toString(),
        trainings = this.trainings.map { trainingEntity -> trainingEntity.toTrainingDTO() }
    )
}

private fun TrainingEntity.toTrainingDTO(): TrainingDTO {
    return TrainingDTO(
        trainingId = this.id.toString(),
        exercises = this.exercises.map { excerciseEntity -> excerciseEntity.toExerciseDTO() }
    )
}

private fun ExerciseEntity.toExerciseDTO(): ExerciseDTO {
    return ExerciseDTO(
        exerciseId = this.id.toString(),
        name = this.name,
        pictureUrl = this.pictureUrl,
        sets = this.sets.map { setEntity -> setEntity.toSetDTO() }
    )
}

private fun SetEntity.toSetDTO(): SetDTO {
    return SetDTO(
        id = this.id.toString(),
        number = this.number,
        kilograms = this.kilograms,
        reps = this.reps
    )
}