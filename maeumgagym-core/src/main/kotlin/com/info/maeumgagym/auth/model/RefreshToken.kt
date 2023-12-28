package com.info.maeumgagym.auth.model

data class RefreshToken(

    val subject: String,

    val rfToken: String,

    val ttl: Long
)
