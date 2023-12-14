package com.assignment.carmanagementsystem.errors

import com.fasterxml.jackson.core.JsonProcessingException
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.Date

@RestControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [EntityNotFoundException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected fun entityNotFoundExceptionHandler(
        e: EntityNotFoundException,
        request: WebRequest,
    ): ErrorResponse {
        return ErrorResponse(e.message, ENTITY_NOT_FOUND_CODE, Date())
    }

    @ExceptionHandler(value = [JsonProcessingException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected fun jsonProcessingExceptionHandler(
        e: JsonProcessingException,
        request: WebRequest,
    ): ErrorResponse {
        return ErrorResponse(e.message, PARSING_EXCEPTION_CODE, Date())
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any> {
        val fieldErrors =
            ex.bindingResult.fieldErrors.associate {
                it.field to it.defaultMessage
            }
        return ResponseEntity(
            ErrorResponseFieldValidation(fieldErrors, REQUEST_PARAMETERS_VALIDATION_CODE, Date()),
            headers,
            status,
        )
    }

    companion object {
        private const val ENTITY_NOT_FOUND_CODE = "ENTITY_NOT_FOUND"
        private const val PARSING_EXCEPTION_CODE = "PARSING_REQUEST_FAILED"
        private const val REQUEST_PARAMETERS_VALIDATION_CODE = "REQUEST_BODY_VALIDATION_FAILED"
    }
}
