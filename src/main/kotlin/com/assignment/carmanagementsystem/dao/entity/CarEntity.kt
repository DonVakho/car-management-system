package com.assignment.carmanagementsystem.dao.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.persistence.Table
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import org.hibernate.annotations.UuidGenerator
import java.io.Serializable
import java.math.BigDecimal
import java.util.Date

@Entity
@Table(name = "cars")
@JsonIgnoreProperties(ignoreUnknown = true)
class CarEntity(
    var brand: String,
    var model: String,
    var color: String,
    @Column(name = "year_of_production")
    var yearOfProduction: Int,
    var price: BigDecimal,
) : Serializable {
    @Temporal(TemporalType.TIMESTAMP)
    var created: Date = Date()

    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    var lastUpdated: Date = Date()

    @Id
    @UuidGenerator
    val id: String = ""

    @PrePersist
    fun onCreate() {
        val currentDate = Date()
        created = currentDate
        lastUpdated = currentDate
    }

    @PreUpdate
    fun onUpdate() {
        lastUpdated = Date()
    }

    override fun toString(): String {
        return """CarEntity(
            |brand='$brand', 
            |model='$model', 
            |color='$color', 
            |yearOfProduction=$yearOfProduction, 
            |price=$price, 
            |created=$created, 
            |lastUpdated=$lastUpdated, 
            |id='$id')"""
            .trimMargin()
    }
}
