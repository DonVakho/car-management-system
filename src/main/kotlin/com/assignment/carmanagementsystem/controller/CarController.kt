package com.assignment.carmanagementsystem.controller

import com.assignment.carmanagementsystem.dto.requests.AddCarDto
import com.assignment.carmanagementsystem.dto.requests.UpdateCarDto
import com.assignment.carmanagementsystem.dto.responses.GetCarDto
import com.assignment.carmanagementsystem.service.CarService
import jakarta.persistence.EntityNotFoundException
import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/cars")
class CarController(
    private val carService: CarService,
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping("/")
    fun listCars(): Collection<GetCarDto> = carService.listCars().map { GetCarDto(it) }

    @GetMapping("/{id}")
    fun getCar(
        @PathVariable id: String,
    ): GetCarDto {
        val carEntity = carService.findById(id)
        if (carEntity == null) {
            val errorMessage = "Car entity with ID: $id was not found in database"
            logger.error(errorMessage)
            throw EntityNotFoundException(errorMessage)
        }
        return GetCarDto(carEntity)
    }

    @PostMapping("/")
    fun addCar(
        @Valid @RequestBody addCarDto: AddCarDto,
    ): GetCarDto {
        val carEntity = carService.addCar(addCarDto)
        logger.debug("Created new CarEntity with id: {}", carEntity.id)
        return GetCarDto(carEntity)
    }

    @PutMapping("/{id}")
    fun updateCar(
        @PathVariable id: String,
        @Valid @RequestBody updateCarDto: UpdateCarDto,
    ): GetCarDto {
        val savedCar = carService.updateCar(id, updateCarDto)
        logger.debug("Updated CarEntity with id {} ", savedCar.id)
        return GetCarDto(savedCar)
    }

    @DeleteMapping("/{id}")
    fun deleteCar(
        @PathVariable id: String,
    ) {
        carService.deleteCar(id)
        logger.debug("Deleted CarEntity with id: {}", id)
    }
}
