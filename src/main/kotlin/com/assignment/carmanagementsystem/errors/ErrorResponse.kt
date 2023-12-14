package com.assignment.carmanagementsystem.errors

import java.util.Date

data class ErrorResponse(
    val message: String?,
    val errorCode: String,
    val time: Date,
)
