package com.info.maeumgagym.core.auth.port.`in`

interface GoogleLoginUseCase {

    fun login(accessToken: String): Pair<String, String>
}
