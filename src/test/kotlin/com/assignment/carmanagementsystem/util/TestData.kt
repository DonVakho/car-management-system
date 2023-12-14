package com.assignment.carmanagementsystem.util

object TestData {
    val toyotaCorollaBlackEntity =
        carEntity {
            brand = "Toyota"
            model = "Corolla"
            price = "12999.99"
        }

    val toyotaPriusRedEntity =
        carEntity {
            brand = "Toyota"
            model = "Prius"
            color = "red"
            price = "12999.99"
        }

    val toyotaCorollaBlackUpdatedEntity =
        carEntity {
            brand = "Toyota"
            model = "Corolla_New"
            price = "15999.99"
        }

    val toyotaPriusRedAddDto =
        addCarDto {
            brand = "Toyota"
            model = "Prius"
            color = "red"
            price = "12999.99"
        }

    val toyotaCorollaBlackUpdateDto =
        updateCarDto {
            brand = "Toyota"
            model = "Corolla_New"
            price = "15999.99"
        }
}
