package com.info.maeumgagym.auth.port.out

interface GetKakaoTokenPort {

    fun getToken(code: String): String
}
