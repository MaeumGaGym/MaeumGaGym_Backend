package com.info.maeumgagym.auth.port.`in`

interface GoogleLoginUseCase {

    fun login(accessToken: String): Pair<String, String>
}
