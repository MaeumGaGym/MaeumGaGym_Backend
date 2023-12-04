package com.info.maeumgagym.auth.port.`in`

import com.info.maeumgagym.auth.dto.request.ReissueRequest
import com.info.maeumgagym.auth.dto.response.TokenResponse

interface ReissueUseCase {

    fun reissue(request: ReissueRequest): TokenResponse
}
