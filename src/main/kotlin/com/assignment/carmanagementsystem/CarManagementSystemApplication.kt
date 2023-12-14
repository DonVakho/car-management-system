package com.assignment.carmanagementsystem

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
class CarManagementSystemApplication

fun main(args: Array<String>) {
    runApplication<CarManagementSystemApplication>(*args)
}
