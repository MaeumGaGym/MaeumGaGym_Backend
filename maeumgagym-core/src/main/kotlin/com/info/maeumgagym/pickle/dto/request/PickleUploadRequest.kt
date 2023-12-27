package com.info.maeumgagym.pickle.dto.request

data class PickleUploadRequest(

    val videoId: Long,

    val title: String,

    val description: String?,

    val tags: MutableSet<String>
)
