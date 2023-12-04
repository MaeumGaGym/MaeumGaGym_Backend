package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.`in`.KakaoDuplicateCheckUseCase
import com.info.maeumgagym.auth.port.out.GetKakaoInfoPort
import com.info.maeumgagym.user.port.out.ExistUserByOAuthIdPort

@UseCase
class KakaoDuplicateCheckUserService(
    private val existUserByOAuthIdPort: ExistUserByOAuthIdPort,
    private val kakaoInfoPort: GetKakaoInfoPort
) : KakaoDuplicateCheckUseCase {
    override fun duplicateCheck(accessToken: String): Boolean {
        val userInfo = kakaoInfoPort.getInfo(accessToken)
        return existUserByOAuthIdPort.existByOAuthId(userInfo.id)
    }
}
