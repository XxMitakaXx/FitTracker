package org.example.fittracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FitTrackerApplication

fun main(args: Array<String>) {
    runApplication<FitTrackerApplication>(*args)
}
