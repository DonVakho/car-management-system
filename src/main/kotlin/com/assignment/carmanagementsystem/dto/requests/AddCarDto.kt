package com.assignment.carmanagementsystem.dto.requests

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal

data class AddCarDto(
    @field:NotBlank(message = "Brand is required")
    val brand: String,
    @field:NotBlank(message = "Model is required")
    val model: String,
    @field:NotBlank(message = "Color is required")
    val color: String,
    @field:Min(value = 0)
    val price: BigDecimal,
    @field:Min(value = 1900)
    @field:Max(value = 2023)
    val yearOfProduction: Int,
)
