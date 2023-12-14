package com.assignment.carmanagementsystem.cars

import com.assignment.carmanagementsystem.dao.repository.CarRepository
import com.assignment.carmanagementsystem.service.CarService
import com.assignment.carmanagementsystem.util.TestData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.BDDMockito.times
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.cache.CacheManager
import java.util.Optional

@SpringBootTest
class CacheTest {
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
    fun test_ListCars_Two_Times() {
        val carsInDb = listOf(TestData.toyotaCorollaBlackEntity, TestData.toyotaPriusRedEntity)
        given(carRepository.findAll()).willReturn(carsInDb)

        val result = carService.listCars()
        val result2 = carService.listCars()

        // assert that result was cached and repository was addressed only once
        then(carRepository).should().findAll()

        assertThat(result).containsExactlyInAnyOrder(TestData.toyotaCorollaBlackEntity, TestData.toyotaPriusRedEntity)

        assertThat(result2).containsExactlyInAnyOrder(TestData.toyotaCorollaBlackEntity, TestData.toyotaPriusRedEntity)
    }

    @Test
    fun test_ListCars_Two_Times_Clear_Cache_In_Between() {
        val carsInDb = listOf(TestData.toyotaCorollaBlackEntity, TestData.toyotaPriusRedEntity)
        given(carRepository.findAll()).willReturn(carsInDb)

        val result = carService.listCars()
        cacheManager.getCache("allCarsCache")?.clear()
        val result2 = carService.listCars()

        // assert that when cache was cleared repository was addressed two times
        then(carRepository).should(times(2)).findAll()

        assertThat(result).containsExactlyInAnyOrder(TestData.toyotaCorollaBlackEntity, TestData.toyotaPriusRedEntity)

        assertThat(result2).containsExactlyInAnyOrder(TestData.toyotaCorollaBlackEntity, TestData.toyotaPriusRedEntity)
    }

    @Test
    fun test_GetCar_Two_Times() {
        given(carRepository.findById("ID")).willReturn(Optional.of(TestData.toyotaPriusRedEntity))

        val result = carService.findById("ID")
        val result2 = carService.findById("ID")

        // assert that result was cached and repository was addressed only once
        then(carRepository).should().findById("ID")

        assertThat(result).isEqualTo(TestData.toyotaPriusRedEntity)
        assertThat(result2).isEqualTo(TestData.toyotaPriusRedEntity)
    }

    @Test
    fun test_GetCar_Two_Times_Clear_Cache_In_Between() {
        given(carRepository.findById("ID")).willReturn(Optional.of(TestData.toyotaPriusRedEntity))

        val result = carService.findById("ID")
        cacheManager.getCache("carCache")?.evict("ID")
        val result2 = carService.findById("ID")

        // assert that when entry was evicted from cache by id repository was addressed two times
        then(carRepository).should(times(2)).findById("ID")

        assertThat(result).isEqualTo(TestData.toyotaPriusRedEntity)
        assertThat(result2).isEqualTo(TestData.toyotaPriusRedEntity)
    }

    @Test
    fun test_Add_Car_Clears_Cache() {
        given(carRepository.findAll()).willReturn(listOf(TestData.toyotaCorollaBlackEntity))
        given(carRepository.save(any())).willReturn(TestData.toyotaPriusRedEntity)

        val result = carService.listCars()

        carService.addCar(TestData.toyotaPriusRedAddDto)
        given(carRepository.findAll()).willReturn(
            listOf(
                TestData.toyotaCorollaBlackEntity,
                TestData.toyotaPriusRedEntity,
            ),
        )

        val result2 = carService.listCars()

        // assert that adding new car will clear cache
        then(carRepository).should(times(2)).findAll()

        assertThat(result).containsExactlyInAnyOrder(TestData.toyotaCorollaBlackEntity)

        assertThat(result2).containsExactlyInAnyOrder(TestData.toyotaCorollaBlackEntity, TestData.toyotaPriusRedEntity)
    }

    @Test
    fun test_Update_Car_Clears_AllCars_Cache() {
        given(carRepository.findAll()).willReturn(listOf(TestData.toyotaCorollaBlackEntity))
        given(carRepository.findById("ID")).willReturn(Optional.of(TestData.toyotaCorollaBlackEntity))
        given(carRepository.save(any())).willReturn(TestData.toyotaCorollaBlackUpdatedEntity)

        val result = carService.listCars()

        carService.updateCar("ID", TestData.toyotaCorollaBlackUpdateDto)
        given(carRepository.findAll()).willReturn(listOf(TestData.toyotaCorollaBlackUpdatedEntity))

        val result2 = carService.listCars()

        // assert that updating entry will clear all cars cache
        then(carRepository).should(times(2)).findAll()

        assertThat(result).containsExactlyInAnyOrder(TestData.toyotaCorollaBlackEntity)
        assertThat(result2).containsExactlyInAnyOrder(TestData.toyotaCorollaBlackUpdatedEntity)
    }

    @Test
    fun test_Update_Car_Clears_CarCache_By_Id() {
        given(carRepository.findById("ID")).willReturn(Optional.of(TestData.toyotaCorollaBlackEntity))
        given(carRepository.save(any())).willReturn(TestData.toyotaCorollaBlackUpdatedEntity)

        val result = carService.findById("ID")

        carService.updateCar("ID", TestData.toyotaCorollaBlackUpdateDto)
        given(carRepository.findById("ID")).willReturn(Optional.of(TestData.toyotaCorollaBlackUpdatedEntity))

        val result2 = carService.findById("ID")

        // assert that updating entry will clear car cache by id (third call is from update method)
        then(carRepository).should(times(3)).findById("ID")

        assertThat(result).isEqualTo(TestData.toyotaCorollaBlackEntity)
        assertThat(result2).isEqualTo(TestData.toyotaCorollaBlackUpdatedEntity)
    }
}
