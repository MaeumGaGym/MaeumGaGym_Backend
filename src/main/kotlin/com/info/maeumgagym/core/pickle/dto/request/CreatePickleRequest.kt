package com.info.maeumgagym.core.pickle.dto.request

data class CreatePickleRequest(

    val videoId: String,

    val title: String,

    val description: String?,

    val tags: MutableSet<String>
)
