package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.exception.AlreadyWithdrawalUserException
import com.info.maeumgagym.auth.exception.UserNotFoundException
import com.info.maeumgagym.auth.port.`in`.KakaoLoginUseCase
import com.info.maeumgagym.auth.port.out.GenerateJwtPort
import com.info.maeumgagym.auth.port.out.GetKakaoInfoPort
import com.info.maeumgagym.user.port.out.FindUserByOAuthIdPort

@UseCase
class KakaoLoginService(
    private val getKakaoInfoPort: GetKakaoInfoPort,
    private val findUserByOAuthIdPort: FindUserByOAuthIdPort,
    private val generateJwtPort: GenerateJwtPort
) : KakaoLoginUseCase {
    override fun login(accessToken: String): TokenResponse {
        val userInfo = getKakaoInfoPort.getInfo(accessToken)
        val user = findUserByOAuthIdPort.findUserByOAuthId(userInfo.id)?.let {
            if (it.isDeleted) {
                throw AlreadyWithdrawalUserException
            } else {
                return@let it
            }
        } ?: throw UserNotFoundException
        return generateJwtPort.generateToken(user.id)
    }
}
