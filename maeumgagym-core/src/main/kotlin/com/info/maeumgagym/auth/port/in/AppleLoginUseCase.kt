package com.info.maeumgagym.auth.port.`in`

interface AppleLoginUseCase {

    fun login(token: String): Pair<String, String>
}
