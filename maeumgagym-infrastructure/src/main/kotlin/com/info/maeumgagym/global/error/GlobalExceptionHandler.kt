package com.info.maeumgagym.global.error

import com.info.maeumgagym.common.exception.MaeumGaGymException
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler(
    private val messageSource: MessageSource
) {
    @ExceptionHandler(MaeumGaGymException::class)
    fun handlingEquusException(e: MaeumGaGymException): ResponseEntity<ErrorResponse> {
        val code = e.errorCode
        return ResponseEntity(
            ErrorResponse(code.status, code.message),
            HttpStatus.valueOf(code.status)
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validatorExceptionHandler(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                400,
                e.bindingResult.allErrors[0].defaultMessage
            ),
            HttpStatus.BAD_REQUEST
        )
    }
}
