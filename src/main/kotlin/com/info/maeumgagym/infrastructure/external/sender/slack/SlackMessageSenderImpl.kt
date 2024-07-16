package com.info.maeumgagym.infrastructure.external.sender.slack

import com.info.maeumgagym.infrastructure.external.sender.dto.Message
import com.info.maeumgagym.infrastructure.external.sender.slack.env.SlackProperties
import com.slack.api.Slack
import com.slack.api.methods.request.chat.ChatPostMessageRequest
import org.springframework.stereotype.Component

@Component
class SlackMessageSenderImpl(
    private val slackProperties: SlackProperties
) : SlackMessageSender {

    override fun send(message: Message) {
        val client = Slack.getInstance().methods(slackProperties.token)

        val chatRequest = ChatPostMessageRequest.builder()
            .token(slackProperties.token)
            .channel(slackProperties.channel)
            .text(generateMessage(message))
            .mrkdwn(true)
            .build()

        client.chatPostMessage(chatRequest)
    }

    private fun generateMessage(message: Message): String {
        var str = "${message.title}\n" +
            message.message

        message.keyValue?.forEach {
            str += "\n*${it.key}* : ${it.value}"
        }

        return str
    }
}
