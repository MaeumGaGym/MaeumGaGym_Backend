package com.info.maeumgagym.infrastructure.error.resolver.impl

import com.info.maeumgagym.common.exception.*
import com.info.maeumgagym.infrastructure.error.resolver.ErrorResolver
import com.info.maeumgagym.infrastructure.error.vo.ErrorInfo
import org.apache.commons.logging.LogFactory
import org.springframework.stereotype.Component

/**
 * [ErrorInfo]를 로그로 출력
 *
 * @author Daybreak312
 * @since 09-05-2024
 */
@Component
class ErrorLogger : ErrorResolver {

    private val logger = LogFactory.getLog(javaClass)

    override fun resolve(errorInfo: ErrorInfo) {

        if (isUnknownMaeumGaGymException(errorInfo.exception)) {
            errorInfo.exception.printStackTrace()
        }

        errorInfo.run {
            logger.warn(
                "[$id] $status : \"$message\" with exception \"${exception.javaClass.name}\""
            )

            logger.warn(
                "\tat ${errorOccurredClassName.first()}"
            )

            errorOccurredClassName.subList(1, errorOccurredClassName.size).forEach {
                logger.warn("\t or $it")
            }
        }
    }

    private fun isUnknownMaeumGaGymException(e: MaeumGaGymException): Boolean =
        e !is BusinessLogicException && e !is SecurityException &&
            e !is FilterException && e !is InterceptorException &&
            e !is AuthenticationException && e !is PresentationValidationException
}
