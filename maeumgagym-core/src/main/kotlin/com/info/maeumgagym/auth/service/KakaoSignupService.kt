package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.exception.DuplicatedNicknameException
import com.info.maeumgagym.auth.port.`in`.KakaoSignupUseCase
import com.info.maeumgagym.auth.port.out.GetKakaoInfoPort
import com.info.maeumgagym.user.model.Role
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.ExistUserByNicknamePort
import com.info.maeumgagym.user.port.out.SaveUserPort

@UseCase
class KakaoSignupService(
    private val kakaoInfoPort: GetKakaoInfoPort,
    private val existUserByNicknamePort: ExistUserByNicknamePort,
    private val saveUserPort: SaveUserPort
) : KakaoSignupUseCase {
    override fun signup(accessToken: String, nickname: String) {
        if (existUserByNicknamePort.existByNickname(nickname)) throw DuplicatedNicknameException
        val userInfo = kakaoInfoPort.getInfo(accessToken)
        saveUserPort.saveUser(
            User(
                nickname = nickname,
                roles = mutableListOf(Role.USER),
                oauthId = userInfo.id
            )
        )
    }
}
