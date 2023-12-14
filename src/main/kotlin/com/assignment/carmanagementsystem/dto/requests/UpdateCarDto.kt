package com.assignment.carmanagementsystem.dto.requests

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import java.math.BigDecimal

data class UpdateCarDto(
    val brand: String?,
    val model: String?,
    val color: String?,
    @field:Min(value = 0)
    val price: BigDecimal?,
    @field:Min(value = 1900)
    @field:Max(value = 2023)
    val yearOfProduction: Int?,
)
