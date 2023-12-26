package com.info.maeumgagym.pickle.dto.request

data class UpdatePickleRequest(

    val title: String,

    val description: String?,

    val tags: MutableSet<String>
)
