package com.info.maeumgagym.core.auth.service

import com.info.maeumgagym.common.annotation.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.`in`.GoogleLoginUseCase
import com.info.maeumgagym.core.auth.port.`in`.GoogleRecoveryUseCase
import com.info.maeumgagym.core.auth.port.`in`.GoogleSignupUseCase
import com.info.maeumgagym.core.auth.port.out.GenerateJwtPort
import com.info.maeumgagym.core.auth.port.out.GetGoogleInfoPort
import com.info.maeumgagym.core.auth.port.out.RevokeGoogleTokenPort
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.core.user.model.Role
import com.info.maeumgagym.core.user.model.User
import com.info.maeumgagym.core.user.port.out.ExistUserPort
import com.info.maeumgagym.core.user.port.out.RecoveryUserPort
import com.info.maeumgagym.core.user.port.out.SaveUserPort

@UseCase
internal class GoogleOAuthService(
    private val getGoogleInfoPort: GetGoogleInfoPort,
    private val saveUserPort: SaveUserPort,
    private val existUserPort: ExistUserPort,
    private val generateJwtPort: GenerateJwtPort,
    private val revokeGoogleTokenPort: RevokeGoogleTokenPort,
    private val recoveryUserPort: RecoveryUserPort
) : GoogleLoginUseCase, GoogleSignupUseCase, GoogleRecoveryUseCase {

    override fun login(accessToken: String): Pair<String, String> {
        // google access_token으로 profile 가져오기
        val profile = getGoogleInfoPort.getGoogleInfo(accessToken)

        // 존재하지 않는 유저라면 NotFound 예외처리
        if (!existUserPort.existsByOAuthId(profile.sub)) throw BusinessLogicException.USER_NOT_FOUND

        // google access_token 만료 시키기
        revokeGoogleTokenPort.revoke(accessToken)

        // subject로 토큰 발급 및 반환
        return generateJwtPort.generateTokens(profile.sub)
    }

    override fun signup(accessToken: String, nickname: String) {
        // nickname 중복 확인
        if (existUserPort.existByNicknameOnWithdrawalSafe(nickname)) throw BusinessLogicException.DUPLICATED_NICKNAME

        // google access_token으로 profile 가져오기
        val profile = getGoogleInfoPort.getGoogleInfo(accessToken)

        // 중복 유저 확인
        if (existUserPort.existByOAuthIdOnWithdrawalSafe(profile.sub)) throw BusinessLogicException.ALREADY_EXIST_USER

        // 유저 생성
        saveUserPort.save(
            User(
                nickname = nickname,
                roles = mutableListOf(Role.USER),
                oauthId = profile.sub,
                profileImage = profile.picture,
                physicalInfoModel = null
            )
        )
    }

    override fun recovery(accessToken: String) {
        // google access_token으로 profile 가져오기
        val profile = getGoogleInfoPort.getGoogleInfo(accessToken)

        // 회원 복구 함수 호출
        recoveryUserPort.recovery(profile.sub)
    }
}
