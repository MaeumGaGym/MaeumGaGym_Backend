package com.info.maeumgagym.global.error

import com.info.maeumgagym.common.exception.MaeumGaGymException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MaeumGaGymException::class)
    protected fun handlingEquusException(e: MaeumGaGymException): ResponseEntity<ErrorResponse> {
        val code = e.errorCode
        return ResponseEntity(
            ErrorResponse(code.status, code.message),
            HttpStatus.valueOf(code.status)
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun validatorExceptionHandler(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                400,
                e.bindingResult.allErrors[0].defaultMessage
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(ConstraintViolationException::class)
    protected fun handleConstraintViolationException(e: ConstraintViolationException): BindErrorResponse {
        val errorMap = HashMap<String, String?>()

        for (error: ConstraintViolation<*> in e.constraintViolations) {
            errorMap[error.propertyPath.toString().split('.').last()] = error.message
        }

        return BindErrorResponse(HttpStatus.BAD_REQUEST, listOf(errorMap))
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    protected fun handleMissingServletRequestParameterException(
        e: MissingServletRequestParameterException
    ): ResponseEntity<ErrorResponse> = ResponseEntity(
        ErrorResponse(
            400,
            e.message
        ),
        HttpStatus.BAD_REQUEST
    )
}
