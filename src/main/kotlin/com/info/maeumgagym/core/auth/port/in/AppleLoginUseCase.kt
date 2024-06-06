package com.info.maeumgagym.core.auth.port.`in`

interface AppleLoginUseCase {

    fun login(token: String): Pair<String, String>
}
