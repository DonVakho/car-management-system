package com.assignment.carmanagementsystem.dto.responses

import com.assignment.carmanagementsystem.dao.entity.CarEntity
import java.math.BigDecimal

class GetCarDto(carEntity: CarEntity) {
    val id: String
    val brand: String
    val model: String
    val color: String
    val price: BigDecimal
    val yearOfProduction: Int

    init {
        this.id = carEntity.id
        this.brand = carEntity.brand
        this.model = carEntity.model
        this.color = carEntity.color
        this.price = carEntity.price
        this.yearOfProduction = carEntity.yearOfProduction
    }
}
