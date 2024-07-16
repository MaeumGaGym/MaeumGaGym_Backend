package com.info.maeumgagym.infrastructure.external.sender

import com.info.maeumgagym.infrastructure.external.sender.dto.Message

/**
 * 임의의 플랫폼을 통해 메세지 전송
 *
 * 여러 개의 하위 구현체가 존재할 수 있음.
 *
 * @author Daybreak312
 * @since 11-07-2024
 */
interface MessageSender {

    fun send(message: Message)
}
