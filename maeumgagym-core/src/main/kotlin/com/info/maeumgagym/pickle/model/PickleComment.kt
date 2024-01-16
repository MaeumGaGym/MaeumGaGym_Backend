package com.info.maeumgagym.pickle.model

import java.time.LocalDateTime
import java.util.UUID

data class PickleComment(
    val id: Long? = null,
    val content: String,
    val videoId: String,
    val writerId: UUID,
    val children: MutableList<PickleReply>? = arrayListOf(),
    val createdAt: LocalDateTime? = null,
    val isDeleted: Boolean = false
)
