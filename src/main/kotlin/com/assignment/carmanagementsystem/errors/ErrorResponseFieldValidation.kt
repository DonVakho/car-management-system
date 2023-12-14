package com.assignment.carmanagementsystem.errors

import java.util.Date

data class ErrorResponseFieldValidation(
    val fieldErrors: Map<String, String?>,
    val errorCode: String,
    val time: Date,
)
