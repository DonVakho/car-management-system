package com.assignment.carmanagementsystem.util

import com.assignment.carmanagementsystem.dao.entity.CarEntity
import com.assignment.carmanagementsystem.dto.requests.AddCarDto
import com.assignment.carmanagementsystem.dto.requests.UpdateCarDto
import java.math.BigDecimal
import java.time.Year

fun carEntity(block: CarBuilder.() -> Unit): CarEntity {
    return CarBuilder().apply(block).buildEntity()
}

fun addCarDto(block: CarBuilder.() -> Unit): AddCarDto {
    return CarBuilder().apply(block).buildAddCarDto()
}

fun updateCarDto(block: CarBuilder.() -> Unit): UpdateCarDto {
    return CarBuilder().apply(block).buildUpdateCarDto()
}

class CarBuilder {
    lateinit var brand: String
    lateinit var model: String
    var color: String = "Black"
    var yearOfProduction: Int = Year.now().value
    private var priceDec: BigDecimal = BigDecimal(0)
    var price = "0"
        set(value) {
            this.priceDec = BigDecimal(value)
        }

    internal fun buildEntity() = CarEntity(brand, model, color, yearOfProduction, priceDec)

    internal fun buildAddCarDto() = AddCarDto(brand, model, color, priceDec, yearOfProduction)

    internal fun buildUpdateCarDto() = UpdateCarDto(brand, model, color, priceDec, yearOfProduction)
}
