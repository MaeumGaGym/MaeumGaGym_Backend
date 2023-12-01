package com.info.maeumgagym.auth.port.`in`

import com.info.maeumgagym.auth.dto.response.TokenResponse

interface AppleLoginUseCase {

    fun execute(token: String): TokenResponse
}
