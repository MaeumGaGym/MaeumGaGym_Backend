package com.info.maeumgagym.auth.port.`in`

interface KakaoLoginUseCase {

    fun login(accessToken: String): Pair<String, String>
}
