package com.info.maeumgagym.auth.port.`in`

import com.info.maeumgagym.auth.dto.response.TokenResponse

interface ReissueUseCase {

    fun reissue(token: String): TokenResponse
}
