package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.exception.AlreadyExistUserException
import com.info.maeumgagym.auth.port.`in`.KakaoRecoveryUseCase
import com.info.maeumgagym.user.exception.UserNotFoundException
import com.info.maeumgagym.auth.port.`in`.KakaoLoginUseCase
import com.info.maeumgagym.auth.port.`in`.KakaoSignupUseCase
import com.info.maeumgagym.auth.port.out.GenerateJwtPort
import com.info.maeumgagym.auth.port.out.GetKakaoInfoPort
import com.info.maeumgagym.user.exception.DuplicatedNicknameException
import com.info.maeumgagym.user.model.Role
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.*

@UseCase
class KakaoOAuthService(
    private val getKakaoInfoPort: GetKakaoInfoPort,
    private val generateJwtPort: GenerateJwtPort,
    private val existUserByOAuthIdPort: ExistUserByOAuthIdPort,
    private val existUserByNicknamePort: ExistUserByNicknamePort,
    private val saveUserPort: SaveUserPort,
    private val recoveryUserPort: RecoveryUserPort
) : KakaoLoginUseCase, KakaoSignupUseCase, KakaoRecoveryUseCase {

    override fun login(accessToken: String): TokenResponse {
        val userInfo = getKakaoInfoPort.getInfo(accessToken)
        if (!existUserByOAuthIdPort.existsUserByOAuthId(userInfo.id)) throw UserNotFoundException
        return generateJwtPort.generateTokens(userInfo.id)
    }

    override fun signup(accessToken: String, nickname: String) {
        if (existUserByNicknamePort.existByNicknameInNative(nickname)) throw DuplicatedNicknameException

        val userInfo = getKakaoInfoPort.getInfo(accessToken)

        if (existUserByOAuthIdPort.existByOAuthIdInNative(userInfo.id)) throw AlreadyExistUserException

        saveUserPort.saveUser(
            User(
                nickname = nickname,
                roles = mutableListOf(Role.USER),
                oauthId = userInfo.id
            )
        )
    }

    override fun recovery(accessToken: String) {
        val userInfo = getKakaoInfoPort.getInfo(accessToken)

        recoveryUserPort.recovery(userInfo.id)
    }
}
