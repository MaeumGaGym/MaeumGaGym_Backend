package com.info.maeumgagym.global.error

import com.info.maeumgagym.common.exception.MaeumGaGymException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class MaeumGaGymExceptionHandler {
    @ExceptionHandler(MaeumGaGymException::class)
    fun handlingDefaultCustomException(e: MaeumGaGymException): ResponseEntity<ErrorResponse> =
        e.errorCode.run {
            ResponseEntity(
                ErrorResponse(status, message),
                HttpStatus.valueOf(status)
            )
        }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun validatorExceptionHandler(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> =
        ResponseEntity(
            ErrorResponse(
                400,
                e.bindingResult.allErrors[0].defaultMessage
            ),
            HttpStatus.BAD_REQUEST
        )

    @ExceptionHandler(ConstraintViolationException::class)
    protected fun handleConstraintViolationException(e: ConstraintViolationException): BindErrorResponse =
        BindErrorResponse(
            HttpStatus.BAD_REQUEST,
            listOf(
                e.constraintViolations.associate {
                    Pair(it.propertyPath.toString().split('.').last(), it.message)
                }
            )
        )

    @ExceptionHandler(MissingServletRequestParameterException::class)
    protected fun handleMissingServletRequestParameterException(
        e: MissingServletRequestParameterException
    ): ResponseEntity<ErrorResponse> =
        ResponseEntity(
            ErrorResponse(
                400,
                e.message
            ),
            HttpStatus.BAD_REQUEST
        )
}

data class ErrorResponse(
    val status: Int,
    val message: String?,
    val timestamp: LocalDateTime = LocalDateTime.now()
)
