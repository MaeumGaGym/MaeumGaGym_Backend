package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.dto.request.ReissueRequest
import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.`in`.ReissueUseCase
import com.info.maeumgagym.auth.port.out.ReissuePort

@UseCase
class ReissueService(
    private val reissuePort: ReissuePort
) : ReissueUseCase {

    override fun reissue(request: ReissueRequest): TokenResponse =
        reissuePort.reissue(request.refreshToken)
}
