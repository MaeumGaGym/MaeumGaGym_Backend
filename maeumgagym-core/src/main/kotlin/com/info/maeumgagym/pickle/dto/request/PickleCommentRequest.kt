package com.info.maeumgagym.pickle.dto.request

data class PickleCommentRequest(
    val content: String,
    val pickleId: String,
    val parentId: Long?
)
