package org.example.fittracker.program.service.util

import org.example.fittracker.program.data.models.ExerciseEntity
import org.example.fittracker.program.data.models.ProgramEntity
import org.example.fittracker.program.data.models.ReviewEntity
import org.example.fittracker.program.data.models.SetEntity
import org.example.fittracker.program.data.models.TrainingDayEntity
import org.example.fittracker.program.data.models.TrainingEntity
import org.example.fittracker.program.data.models.TrainingWeekEntity
import org.example.fittracker.program.data.models.enums.DaysPerWeek
import org.example.fittracker.program.data.models.enums.Equipment
import org.example.fittracker.program.data.models.enums.TrainingLevel
import org.example.fittracker.program.data.models.enums.TrainingType
import org.example.fittracker.program.data.models.enums.WeekDay
import org.example.fittracker.program.service.dto.CreateProgramDTO
import org.example.fittracker.program.service.dto.ExerciseDTO
import org.example.fittracker.program.service.dto.ProgramDTO
import org.example.fittracker.program.service.dto.ReviewDTO
import org.example.fittracker.program.service.dto.SetDTO
import org.example.fittracker.program.service.dto.TrainingDTO
import org.example.fittracker.program.service.dto.TrainingDayDTO
import org.example.fittracker.program.service.dto.TrainingWeekDTO
import org.example.fittracker.user.data.models.UserEntity
import org.springframework.data.mapping.context.PersistentEntities
import kotlin.collections.map

fun ProgramEntity.toProgramDTO(): ProgramDTO {
    val trainingWeeksDTOs = this.weekEntities.map { trainingWeek ->
        val trainingDaysDTOs = trainingWeek.dayEntities.map { trainingDay ->
            val trainingsDTOs = trainingDay.trainings.map { training ->
                val exercisesDTOs = training.exercises.map { exercise ->
                    val setsDTOs = exercise.sets.map { set -> set.toSetDTO()}
                    exercise.toExerciseDTO(setsDTOs = setsDTOs)
                }
                training.toTrainingDTO(exercisesDTOs = exercisesDTOs)
            }
            trainingDay.toTrainingDayDTO(trainingsDTOs = trainingsDTOs)
        }
        trainingWeek.toTrainingWeekDTO(trainingDaysDTOs = trainingDaysDTOs)
    }

    return ProgramDTO(
        id = this.id!!.toString(),
        name = this.name,
        creatorId = this.creator.id.toString(),
        creatorFullName = this.creator.firstName + " " + this.creator.lastName,
        totalCountUsed = this.totalCountUsed,
        trainingWeeks = trainingWeeksDTOs,
        trainingLevel =this.trainingLevel.toString(),
        trainingType = this.trainingType.toString(),
        daysPerWeek = this.daysPerWeek.toString(),
        recommendedDays = this.recommendedDays.map { recommendedDay -> recommendedDay.toString() },
        timePerWorkoutMinutes = this.timePerWorkoutMinutes,
        rate = this.rate ?: 0.0,
        equipment = equipment.toString(),
        reviews = this.reviewEntities.map { reviewEntity -> reviewEntity.toReviewDTO() },
    )
}

fun ProgramDTO.toProgramEntity(creator: UserEntity): ProgramEntity {
    val trainingWeekEntities = this.trainingWeeks.map { trainingWeekDTO ->
        val trainingDayEntities = trainingWeekDTO.trainingDays.map { trainingDayDTO ->
            val trainingEntities = trainingDayDTO.trainings.map { trainingDTO ->
                val exerciseEntities = trainingDTO.exercises.map { exerciseDTO ->
                    val sets = exerciseDTO.sets.map { setDTO ->
                        setDTO.toSetEntity()
                    }
                    exerciseDTO.toExerciseEntity(setEntities = sets)
                }
                trainingDTO.toTrainingEntity(exerciseEntities = exerciseEntities)
            }
            trainingDayDTO.toTrainingDayEntity(trainingEntities = trainingEntities)
        }
        trainingWeekDTO.toTrainingWeekEntity(trainingDayEntities = trainingDayEntities)
    }

    return ProgramEntity(
        name = this.name,
        creator = creator,
        weekEntities = trainingWeekEntities,
        trainingLevel = TrainingLevel.valueOf(value = this.trainingLevel),
        trainingType = TrainingType.valueOf(value = this.trainingType),
        daysPerWeek = DaysPerWeek.valueOf(value = this.daysPerWeek),
        recommendedDays = this.recommendedDays.map { recommendedDayString -> WeekDay.valueOf(value = recommendedDayString) },
        timePerWorkoutMinutes = this.timePerWorkoutMinutes,
        equipment = Equipment.valueOf(value = this.equipment)
    )
}

fun CreateProgramDTO.toProgramEntity(creator: UserEntity): ProgramEntity {
    val trainingWeekEntities = this.trainingWeeks.map { trainingWeekDTO ->
        val trainingDayEntities = trainingWeekDTO.trainingDays.map { trainingDayDTO ->
            val trainingEntities = trainingDayDTO.trainings.map { trainingDTO ->
                val exerciseEntities = trainingDTO.exercises.map { exerciseDTO ->
                    val sets = exerciseDTO.sets.map { setDTO ->
                        setDTO.toSetEntity()
                    }
                    exerciseDTO.toExerciseEntity(setEntities = sets)
                }
                trainingDTO.toTrainingEntity(exerciseEntities = exerciseEntities)
            }
            trainingDayDTO.toTrainingDayEntity(trainingEntities = trainingEntities)
        }
        trainingWeekDTO.toTrainingWeekEntity(trainingDayEntities = trainingDayEntities)
    }

    return ProgramEntity(
        name = this.name,
        creator = creator,
        weekEntities = trainingWeekEntities,
        trainingLevel = TrainingLevel.valueOf(value = this.trainingLevel),
        trainingType = TrainingType.valueOf(value = this.trainingType),
        daysPerWeek = DaysPerWeek.valueOf(value = this.daysPerWeek),
        recommendedDays = this.recommendedDays.map { recommendedDayString -> WeekDay.valueOf(value = recommendedDayString) },
        timePerWorkoutMinutes = this.timePerWorkoutMinutes,
        equipment = Equipment.valueOf(value = this.equipment)
    )
}

private fun TrainingWeekDTO.toTrainingWeekEntity(trainingDayEntities: List<TrainingDayEntity>): TrainingWeekEntity {
    return TrainingWeekEntity(
        number = this.number,
        dayEntities = trainingDayEntities
    )
}

private fun TrainingDayDTO.toTrainingDayEntity(trainingEntities: List<TrainingEntity>): TrainingDayEntity {
    return TrainingDayEntity(
        weekDay = WeekDay.valueOf(value = this.weekDay),
        trainings = trainingEntities
    )
}

private fun TrainingDTO.toTrainingEntity(exerciseEntities: List<ExerciseEntity>): TrainingEntity {
    return TrainingEntity(
        exercises = exerciseEntities
    )
}

private fun ExerciseDTO.toExerciseEntity(setEntities: List<SetEntity>): ExerciseEntity {
    return ExerciseEntity(
        name = this.name,
        pictureUrl = this.pictureUrl,
        sets = setEntities
    )
}


private fun SetDTO.toSetEntity(): SetEntity {
    return SetEntity(
        kilograms = this.kilograms,
        reps = this.reps,
        number = this.number
    )
}


private fun TrainingWeekEntity.toTrainingWeekDTO(trainingDaysDTOs: List<TrainingDayDTO>): TrainingWeekDTO {
    return TrainingWeekDTO(
        trainingWeekId = this.id!!.toString(),
        number = this.number,
        trainingDays = trainingDaysDTOs
    )
}

private fun TrainingDayEntity.toTrainingDayDTO(trainingsDTOs: List<TrainingDTO>): TrainingDayDTO {
    return TrainingDayDTO(
        trainingDayId = this.id!!.toString(),
        weekDay = this.weekDay.toString(),
        trainings = trainingsDTOs
    )
}

private fun TrainingEntity.toTrainingDTO(exercisesDTOs: List<ExerciseDTO>): TrainingDTO {
    return TrainingDTO(
        trainingId = this.id!!.toString(),
        exercises = exercisesDTOs
    )
}

private fun ExerciseEntity.toExerciseDTO(setsDTOs: List<SetDTO>): ExerciseDTO {
    return ExerciseDTO(
        exerciseId = this.id!!.toString(),
        name = this.name,
        pictureUrl = this.pictureUrl,
        sets = setsDTOs
    )
}

private fun SetEntity.toSetDTO(): SetDTO {
    return SetDTO(
        id = this.id!!.toString(),
        kilograms = this.kilograms,
        number = this.number,
        reps = this.reps
    )
}

private fun ReviewEntity.toReviewDTO(): ReviewDTO {
    return ReviewDTO(
        id = this.id!!.toString(),
        userFullName = this.user.firstName + " " + this.user.lastName,
        stars = this.stars,
        strengthGainRate = this.strengthGainRate.toString(),
        muscleGainRate = this.muscleGainRate.toString(),
        anyModification = this.anyModification.toString(),
        review = this.review,
    )
}

