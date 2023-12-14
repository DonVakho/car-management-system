package com.assignment.carmanagementsystem.service

import com.assignment.carmanagementsystem.dao.entity.CarEntity
import com.assignment.carmanagementsystem.dao.repository.CarRepository
import com.assignment.carmanagementsystem.dto.CarMapper
import com.assignment.carmanagementsystem.dto.requests.AddCarDto
import com.assignment.carmanagementsystem.dto.requests.UpdateCarDto
import jakarta.persistence.EntityNotFoundException
import mu.KotlinLogging
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service

@Service
class CarService(
    private val carRepository: CarRepository,
    private val carMapper: CarMapper,
) {
    private val logger = KotlinLogging.logger {}

    @Cacheable(value = ["allCarsCache"])
    fun listCars(): Collection<CarEntity> = carRepository.findAll()

    @Cacheable(value = ["carCache"], key = "#carId")
    fun findById(carId: String): CarEntity? = carRepository.findById(carId).orElse(null)

    @CacheEvict(value = ["allCarsCache"], allEntries = true)
    fun addCar(car: AddCarDto): CarEntity {
        return carRepository.save(carMapper.toCarEntity(car))
    }

    @Caching(
        evict = [
            CacheEvict(value = ["allCarsCache"], allEntries = true),
            CacheEvict(value = ["carCache"], key = "#id"),
        ],
    )
    fun updateCar(
        id: String,
        updatedCar: UpdateCarDto,
    ): CarEntity {
        val existingCar = findById(id)
        if (existingCar == null) {
            val errorMessage = String.format("Car entity with ID: '%s' was not found in database", id)
            logger.error(errorMessage)
            throw EntityNotFoundException(errorMessage)
        }
        return carRepository.save(carMapper.merge(existingCar, updatedCar))
    }

    @Caching(
        evict = [
            CacheEvict(value = ["allCarsCache"], allEntries = true),
            CacheEvict(value = ["carCache"], key = "#carId"),
        ],
    )
    fun deleteCar(carId: String) {
        carRepository.deleteById(carId)
    }
}
