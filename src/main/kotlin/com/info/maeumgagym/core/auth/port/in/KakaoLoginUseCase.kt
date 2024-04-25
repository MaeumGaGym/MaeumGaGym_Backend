package com.info.maeumgagym.core.auth.port.`in`

interface KakaoLoginUseCase {

    fun login(accessToken: String): Pair<String, String>
}
