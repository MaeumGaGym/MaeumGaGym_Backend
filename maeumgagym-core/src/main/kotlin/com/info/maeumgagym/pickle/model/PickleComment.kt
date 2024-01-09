package com.info.maeumgagym.pickle.model

import java.time.LocalDateTime
import java.util.UUID

data class PickleComment(
    val id: Long? = null,
    val content: String,
    val pickleId: String,
    val writerId: UUID,
    val parentComment: PickleComment?,
    val children: MutableList<PickleComment>? = arrayListOf(),
    val createdAt: LocalDateTime? = null,
    val isDeleted: Boolean = false
)
