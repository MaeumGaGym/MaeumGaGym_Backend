package com.info.maeumgagym.auth.model

data class AccessToken (

    val subject: String,

    val accessToken: String,

    val ttl: Long
)
