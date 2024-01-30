package com.info.maeumgagym.pickle.dto.request

data class CreatePickleRequest(

    val videoId: String,

    val title: String,

    val description: String?,

    val tags: MutableSet<String>
)
