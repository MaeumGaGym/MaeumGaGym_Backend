package com.info.maeumgagym.auth.port.out

import com.info.maeumgagym.auth.dto.response.TokenResponse

interface ReissuePort {

    fun reissue(refreshToken: String): TokenResponse
}
