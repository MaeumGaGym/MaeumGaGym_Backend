package com.info.maeumgagym.auth.port.`in`

interface KakaoDuplicateCheckUseCase {
    fun duplicateCheck(accessToken: String): Boolean
}
