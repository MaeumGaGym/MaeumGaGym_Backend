package com.info.maeumgagym.core.auth.service

import com.info.maeumgagym.common.annotation.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.`in`.KakaoLoginUseCase
import com.info.maeumgagym.core.auth.port.`in`.KakaoRecoveryUseCase
import com.info.maeumgagym.core.auth.port.`in`.KakaoSignupUseCase
import com.info.maeumgagym.core.auth.port.out.*
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.core.user.model.Role
import com.info.maeumgagym.core.user.model.User
import com.info.maeumgagym.core.user.port.out.ExistUserPort
import com.info.maeumgagym.core.user.port.out.RecoveryUserPort
import com.info.maeumgagym.core.user.port.out.SaveUserPort

@UseCase
internal class KakaoOAuthService(
    private val getKakaoProfilePort: GetKakaoProfilePort,
    private val generateJwtPort: GenerateJwtPort,
    private val existUserPort: ExistUserPort,
    private val saveUserPort: SaveUserPort,
    private val recoveryUserPort: RecoveryUserPort,
    private val getKakaoTokenPort: GetKakaoTokenPort,
    private val revokeKakaoTokenPort: RevokeKakaoTokenPort
) : KakaoLoginUseCase, KakaoSignupUseCase, KakaoRecoveryUseCase, KakaoGenerateTokenUseCase {

    override fun login(accessToken: String): Pair<String, String> {
        // kakao access_token으로 유저 정보 가져오기
        val userInfo = getKakaoProfilePort.getProfile(accessToken)

        // 존재하지 않는 유저라면 NotFound 예외처리
        if (!existUserPort.existsByOAuthId(userInfo.id)) throw BusinessLogicException.USER_NOT_FOUND

        revokeKakaoTokenPort.revoke(accessToken)

        // subject로 토큰 발급 및 반환
        return generateJwtPort.generateTokens(userInfo.id)
    }

    override fun signup(accessToken: String, nickname: String) {
        // nickname 중복 확인
        if (existUserPort.existByNicknameOnWithdrawalSafe(nickname)) throw BusinessLogicException.DUPLICATED_NICKNAME

        // kakao access_token으로 유저 정보 가져오기
        val userInfo = getKakaoProfilePort.getProfile(accessToken)

        // 중복 유저 확인
        if (existUserPort.existByOAuthIdOnWithdrawalSafe(userInfo.id)) throw BusinessLogicException.ALREADY_EXIST_USER

        // 유저 생성
        saveUserPort.save(
            User(
                nickname = nickname,
                roles = mutableListOf(Role.USER),
                oauthId = userInfo.id,
                physicalInfoModel = null
            )
        )
    }

    override fun recovery(accessToken: String) {
        // kakao access_token으로 유저 정보 가져오기
        val userInfo = getKakaoProfilePort.getProfile(accessToken)

        // 회원 복구 함수 호출
        recoveryUserPort.recovery(userInfo.id)
    }

    override fun generate(code: String): String = getKakaoTokenPort.getToken(code)
}
