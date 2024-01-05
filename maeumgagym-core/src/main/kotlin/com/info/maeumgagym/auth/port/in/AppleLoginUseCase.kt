package com.info.maeumgagym.auth.port.`in`

import com.info.maeumgagym.auth.dto.response.TokenResponse

interface AppleLoginUseCase {

    fun login(token: String): TokenResponse
}
