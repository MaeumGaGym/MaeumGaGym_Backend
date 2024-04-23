package com.info.maeumgagym.error.handler

import com.info.maeumgagym.infrastructure.error.repository.ExceptionRepository
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.DateTimeException

@ControllerAdvice
class CustomExceptionHandler(
    private val exceptionRepository: com.info.maeumgagym.infrastructure.error.repository.ExceptionRepository
) {

    @ExceptionHandler(DateTimeException::class)
    fun dateTimeExceptionHandler(e: DateTimeException) {
        exceptionRepository.save(e)
    }
}
