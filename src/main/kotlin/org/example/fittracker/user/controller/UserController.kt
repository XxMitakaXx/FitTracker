package org.example.fittracker.user.controller

import org.example.fittracker.user.service.UserService
import org.example.fittracker.user.service.dtos.ProgressBodyWeightDTO
import org.example.fittracker.user.service.dtos.UserStartingTrainingStatsDTO
import org.example.fittracker.user.service.dtos.UserStatsDTO
import org.example.fittracker.user.service.dtos.UserTrainingDataDTO
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping("/user-training-data")
    fun getUserTrainingData(): UserTrainingDataDTO {
        return userService.findUserTrainDataById()
    }

    @PostMapping("/user-training-data")
    fun createUserStats(@RequestBody userStatsDTO: UserStatsDTO) {
        userService.saveUserStats(userStatsDTO = userStatsDTO)
    }

    @PostMapping("/user-starter-training-data")
    fun createUserStartingTrainingData(@RequestBody userStartingTrainingStatsDTO: UserStartingTrainingStatsDTO) {
        userService.saveUserStartingTrainingData(userStartingTrainingStatsDTO = userStartingTrainingStatsDTO)
    }

    @PostMapping("/progress_weight")
    fun saveProgressWeight(@RequestBody progressBodyWeightDTO: ProgressBodyWeightDTO) {
        userService.saveProgressWeight(progressBodyWeightDTO = progressBodyWeightDTO)
    }

    @DeleteMapping("progress_bodyweight")
    fun deleteBodyWeight(@RequestBody progressBodyWeightDTO: ProgressBodyWeightDTO) {
        userService.deleteProgressWeight(progressBodyWeightDTO = progressBodyWeightDTO)
    }
}