package com.info.maeumgagym.core.auth.port.out

interface GetKakaoTokenPort {

    fun getToken(code: String): String
}
