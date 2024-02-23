package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.`in`.KakaoLoginUseCase
import com.info.maeumgagym.auth.port.`in`.KakaoRecoveryUseCase
import com.info.maeumgagym.auth.port.`in`.KakaoSignupUseCase
import com.info.maeumgagym.auth.port.out.GenerateJwtPort
import com.info.maeumgagym.auth.port.out.GetKakaoInfoPort
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.user.model.Role
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.ExistUserPort
import com.info.maeumgagym.user.port.out.RecoveryUserPort
import com.info.maeumgagym.user.port.out.SaveUserPort

@UseCase
internal class KakaoOAuthService(
    private val getKakaoInfoPort: GetKakaoInfoPort,
    private val generateJwtPort: GenerateJwtPort,
    private val existUserPort: ExistUserPort,
    private val saveUserPort: SaveUserPort,
    private val recoveryUserPort: RecoveryUserPort
) : KakaoLoginUseCase, KakaoSignupUseCase, KakaoRecoveryUseCase {

    override fun login(accessToken: String): Pair<String, String> {
        // kakao access_token으로 유저 정보 가져오기
        val userInfo = getKakaoInfoPort.getInfo(accessToken)

        // 존재하지 않는 유저라면 NotFound 예외처리
        if (!existUserPort.existsByOAuthId(userInfo.id)) throw BusinessLogicException.USER_NOT_FOUND

        // subject로 토큰 발급 및 반환
        return generateJwtPort.generateTokens(userInfo.id)
    }

    override fun signup(accessToken: String, nickname: String) {
        // nickname 중복 확인
        if (existUserPort.existByNicknameOnWithdrawalSafe(nickname)) throw BusinessLogicException.DUPLICATED_NICKNAME

        // kakao access_token으로 유저 정보 가져오기
        val userInfo = getKakaoInfoPort.getInfo(accessToken)

        // 중복 유저 확인
        if (existUserPort.existByOAuthIdOnWithdrawalSafe(userInfo.id)) throw BusinessLogicException.ALREADY_EXIST_USER

        // 유저 생성
        saveUserPort.save(
            User(
                nickname = nickname,
                roles = mutableListOf(Role.USER),
                oauthId = userInfo.id
            )
        )
    }

    override fun recovery(accessToken: String) {
        // kakao access_token으로 유저 정보 가져오기
        val userInfo = getKakaoInfoPort.getInfo(accessToken)

        // 회원 복구 함수 호출
        recoveryUserPort.recovery(userInfo.id)
    }
}
