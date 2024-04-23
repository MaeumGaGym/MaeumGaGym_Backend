package com.info.maeumgagym.core.pickle.dto.request

data class UpdatePickleRequest(

    val title: String,

    val description: String?,

    val tags: MutableSet<String>
)
