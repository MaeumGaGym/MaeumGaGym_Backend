package com.info.maeumgagym.infrastructure.error.resolver.impl

import com.info.maeumgagym.infrastructure.error.resolver.ErrorResolver
import com.info.maeumgagym.infrastructure.error.vo.ErrorInfo
import com.info.maeumgagym.infrastructure.external.notificator.ExternalSystemNotificator
import com.info.maeumgagym.infrastructure.external.sender.dto.Message
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ErrorNotificator(
    private val externalSystemNotificator: ExternalSystemNotificator
) : ErrorResolver {

    override fun resolve(
        errorInfo: ErrorInfo,
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        if (errorInfo.status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return
        }

        externalSystemNotificator.sendMessage(
            Message(
                title = "[Maeumgagym] Internal Server Error",
                message = "",
                keyValue = mapOf(
                    "Exception Message" to errorInfo.message,
                    "In" to errorInfo.errorOccurredClassName[1]
                )
            )
        )
    }
}
