package com.info.maeumgagym.infrastructure.error.logger

import com.info.maeumgagym.infrastructure.error.vo.ErrorInfo
import org.apache.commons.logging.LogFactory
import org.springframework.stereotype.Component

@Component
class ErrorLoggerImpl : ErrorLogger {

    private val logger = LogFactory.getLog(javaClass)

    override fun printLog(errorInfo: ErrorInfo) {
        errorInfo.run {
            logger.warn(
                "[$id] $status : \"$message\" with exception : \"$exceptionClassName\""
            )

            logger.warn(
                "\tin ${errorOccurredClassName.first()}"
            )

            errorOccurredClassName.subList(1, errorOccurredClassName.size).forEach {
                logger.warn("\tor $it")
            }
        }
    }
}
