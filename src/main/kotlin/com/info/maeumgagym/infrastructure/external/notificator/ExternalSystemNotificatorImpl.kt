package com.info.maeumgagym.infrastructure.external.notificator

import com.info.maeumgagym.infrastructure.collector.bean.ClassBasedBeanCollector
import com.info.maeumgagym.infrastructure.external.sender.MessageSender
import com.info.maeumgagym.infrastructure.external.sender.dto.Message
import org.springframework.stereotype.Component

@Component
class ExternalSystemNotificatorImpl(
    private val classBasedBeanCollector: ClassBasedBeanCollector
) : ExternalSystemNotificator {

    private lateinit var messageSenders: List<MessageSender>

    private var initialized = false

    private fun init() {
        if (!initialized) {
            messageSenders = classBasedBeanCollector.getBeans(MessageSender::class).values.toList()
        }
    }

    override fun sendMessage(message: Message) {
        init()

        messageSenders.forEach {
            it.send(message)
        }
    }
}
