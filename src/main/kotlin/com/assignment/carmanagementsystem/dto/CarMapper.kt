package com.assignment.carmanagementsystem.dto

import com.assignment.carmanagementsystem.dao.entity.CarEntity
import com.assignment.carmanagementsystem.dto.requests.AddCarDto
import com.assignment.carmanagementsystem.dto.requests.UpdateCarDto
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class CarMapper {
    private val objectMapper = ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)

    fun toCarEntity(createCarDto: AddCarDto): CarEntity =
        CarEntity(
            createCarDto.brand,
            createCarDto.model,
            createCarDto.color,
            createCarDto.yearOfProduction,
            createCarDto.price,
        )

    fun merge(
        carEntity: CarEntity,
        updateCarDto: UpdateCarDto,
    ): CarEntity {
        objectMapper.readerForUpdating(carEntity).readValue<String>(objectMapper.writeValueAsString(updateCarDto))
        return carEntity
    }
}
