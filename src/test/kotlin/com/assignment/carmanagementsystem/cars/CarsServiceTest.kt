package com.assignment.carmanagementsystem.cars

import com.assignment.carmanagementsystem.dao.repository.CarRepository
import com.assignment.carmanagementsystem.service.CarService
import com.assignment.carmanagementsystem.util.TestData
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatNoException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.cache.CacheManager
import java.util.Optional

@SpringBootTest
@ExtendWith(MockitoExtension::class)
class CarsServiceTest {
    @MockBean
    private lateinit var carRepository: CarRepository

    @Autowired
    private lateinit var carService: CarService

    @Autowired
    private lateinit var cacheManager: CacheManager

    @BeforeEach
    fun clearCache() {
        cacheManager.getCache("allCarsCache")?.clear()
        cacheManager.getCache("carCache")?.clear()
    }

    @Test
    fun testListCarsTest() {
        val carsInDb = listOf(TestData.toyotaCorollaBlackEntity, TestData.toyotaPriusRedEntity)
        given(carRepository.findAll()).willReturn(carsInDb)

        val result = carService.listCars()

        then(carRepository).should().findAll()

        assertThat(result)
            .containsExactlyInAnyOrder(TestData.toyotaCorollaBlackEntity, TestData.toyotaPriusRedEntity)
    }

    @Test
    fun testGetCar() {
        given(carRepository.findById("ID")).willReturn(Optional.of(TestData.toyotaPriusRedEntity))

        val result = carService.findById("ID")

        then(carRepository).should().findById("ID")

        assertThat(result)
            .isEqualTo(TestData.toyotaPriusRedEntity)
    }

    @Test
    fun testAddCar() {
        given(carRepository.save(any())).willReturn(TestData.toyotaPriusRedEntity)

        val result = carService.addCar(TestData.toyotaPriusRedAddDto)

        then(carRepository).should().save(any())

        assertThat(result)
            .isEqualTo(TestData.toyotaPriusRedEntity)
    }

    @Test
    fun testUpdate() {
        given(carRepository.findById("ID")).willReturn(Optional.of(TestData.toyotaCorollaBlackEntity))
        given(carRepository.save(any())).willReturn(TestData.toyotaCorollaBlackUpdatedEntity)

        val result = carService.updateCar("ID", TestData.toyotaCorollaBlackUpdateDto)

        then(carRepository).should().findById("ID")
        then(carRepository).should().save(any())

        assertThat(result)
            .isEqualTo(TestData.toyotaCorollaBlackUpdatedEntity)
    }

    @Test
    fun testDeleteCar() {
        assertThatNoException().isThrownBy {
            carService.deleteCar("ID")
        }

        then(carRepository).should().deleteById("ID")
    }
}
