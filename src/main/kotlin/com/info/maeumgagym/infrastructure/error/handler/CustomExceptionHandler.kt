package com.info.maeumgagym.infrastructure.error.handler

import com.info.maeumgagym.infrastructure.error.repository.ExceptionRepository
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.DateTimeException

@ControllerAdvice
class CustomExceptionHandler(
    private val exceptionRepository: ExceptionRepository
) {

    @ExceptionHandler(DateTimeException::class)
    fun dateTimeExceptionHandler(e: DateTimeException) {
        exceptionRepository.save(e)
    }
}
