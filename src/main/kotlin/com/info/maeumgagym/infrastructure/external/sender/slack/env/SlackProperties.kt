package com.info.maeumgagym.infrastructure.external.sender.slack.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("slack")
class SlackProperties(
    val token: String,
    val channel: String
)
