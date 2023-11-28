package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.`in`.KakaoLoginUseCase
import com.info.maeumgagym.auth.port.out.GenerateJwtPort
import com.info.maeumgagym.auth.port.out.GetKakaoInfoPort
import com.info.maeumgagym.user.model.Role
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.CreateUserPort
import com.info.maeumgagym.user.port.out.FindUserByOAuthIdPort

@UseCase
class KakaoLoginService(
    private val getKakaoInfoPort: GetKakaoInfoPort,
    private val findUserByOAuthIdPort: FindUserByOAuthIdPort,
    private val generateJwtPort: GenerateJwtPort,
    private val createUserPort: CreateUserPort
): KakaoLoginUseCase {
    override fun login(accessToken: String): TokenResponse {
        val userInfo = getKakaoInfoPort.getInfo(accessToken)
        findUserByOAuthIdPort.findUserByOAuthId(userInfo.oauthId)?.let {
            return generateJwtPort.generateToken(it.id)
        }
        val user = createUserPort.createUser(User(
            nickname = userInfo.properties.nickname,
            roles = mutableListOf(Role.USER)
        ))
        return generateJwtPort.generateToken(user.id)
    }
}
