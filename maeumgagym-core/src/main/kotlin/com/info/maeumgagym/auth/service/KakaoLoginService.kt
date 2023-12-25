package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.user.exception.UserNotFoundException
import com.info.maeumgagym.auth.port.`in`.KakaoLoginUseCase
import com.info.maeumgagym.auth.port.out.GenerateJwtPort
import com.info.maeumgagym.auth.port.out.GetKakaoInfoPort
import com.info.maeumgagym.user.port.out.ExistUserByOAuthIdPort

@UseCase
class KakaoLoginService(
    private val getKakaoInfoPort: GetKakaoInfoPort,
    private val generateJwtPort: GenerateJwtPort,
    private val existUserByOAuthIdPort: ExistUserByOAuthIdPort
) : KakaoLoginUseCase {
    override fun login(accessToken: String): TokenResponse {
        val userInfo = getKakaoInfoPort.getInfo(accessToken)
        if (!existUserByOAuthIdPort.existByOAuthId(userInfo.id)) throw UserNotFoundException
        return generateJwtPort.generateToken(userInfo.id)
    }
}
