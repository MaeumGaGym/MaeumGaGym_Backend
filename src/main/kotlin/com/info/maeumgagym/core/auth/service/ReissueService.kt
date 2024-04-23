package com.info.maeumgagym.core.auth.service

import com.info.common.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.`in`.ReissueUseCase
import com.info.maeumgagym.core.auth.port.out.ReissuePort

@UseCase
internal class ReissueService(
    private val reissuePort: ReissuePort
) : ReissueUseCase {

    override fun reissue(token: String): Pair<String, String> =
        reissuePort.reissue(token)
}
