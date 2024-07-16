package com.info.maeumgagym.infrastructure.external.sender.dto

data class Message(
    val title: String?,
    val message: String?,
    val keyValue: Map<String, String>?
)
