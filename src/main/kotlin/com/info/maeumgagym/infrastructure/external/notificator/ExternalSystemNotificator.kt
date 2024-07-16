package com.info.maeumgagym.infrastructure.external.notificator

import com.info.maeumgagym.infrastructure.external.sender.dto.Message

/**
 * 등록된 외부의 서비스로 메세지를 송신
 *
 * @author Daybreak312
 * @since 11-07-2024
 */
interface ExternalSystemNotificator {

    fun sendMessage(message: Message)
}
