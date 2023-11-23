package com.info.maeumgagym.auth.port.out

interface JwtExpiredCheckPort {

    fun getSubjectWithExpiredCheck(token: String): String
}
