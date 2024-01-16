package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.`in`.ReissueUseCase
import com.info.maeumgagym.auth.port.out.ReissuePort

@UseCase
internal class ReissueService(
    private val reissuePort: ReissuePort
) : ReissueUseCase {

    override fun reissue(token: String): TokenResponse =
        reissuePort.reissue(token)
}
