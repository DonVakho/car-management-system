package com.assignment.carmanagementsystem.controller

import com.assignment.carmanagementsystem.dto.requests.AddCarDto
import com.assignment.carmanagementsystem.dto.requests.UpdateCarDto
import com.assignment.carmanagementsystem.dto.responses.GetCarDto
import com.assignment.carmanagementsystem.service.CarService
import jakarta.persistence.EntityNotFoundException
import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated

@Validated
@Controller
class CarGraphqlController(
    private val carService: CarService,
) {
    private val logger = KotlinLogging.logger {}

    @QueryMapping
    fun listCars(): Collection<GetCarDto> = carService.listCars().map { GetCarDto(it) }

    @QueryMapping
    fun getCar(
        @Argument id: String,
    ): GetCarDto {
        val carEntity = carService.findById(id)
        if (carEntity == null) {
            val errorMessage = "Car entity with ID: $id was not found in database"
            logger.error(errorMessage)
            throw EntityNotFoundException(errorMessage)
        }
        return GetCarDto(carEntity)
    }

    @MutationMapping
    fun addCar(
        @Valid @Argument addCarDto: AddCarDto,
    ): GetCarDto {
        val carEntity = carService.addCar(addCarDto)
        logger.debug("Created new CarEntity with id: {}", carEntity.id)
        return GetCarDto(carEntity)
    }

    @MutationMapping
    fun updateCar(
        @Argument id: String,
        @Valid @Argument updateCarDto: UpdateCarDto,
    ): GetCarDto {
        val savedCar = carService.updateCar(id, updateCarDto)
        logger.debug("Updated CarEntity with id {} ", savedCar.id)
        return GetCarDto(savedCar)
    }

    @MutationMapping
    fun deleteCar(
        @Argument id: String,
    ) {
        carService.deleteCar(id)
        logger.debug("Deleted CarEntity with id: {}", id)
    }
}
