package com.assignment.carmanagementsystem.dao.repository

import com.assignment.carmanagementsystem.dao.entity.CarEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CarRepository : JpaRepository<CarEntity, String>
