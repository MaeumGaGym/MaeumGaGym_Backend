package com.info.maeumgagym.core.auth.service

import com.info.maeumgagym.common.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.`in`.AppleLoginUseCase
import com.info.maeumgagym.core.auth.port.`in`.AppleRecoveryUseCase
import com.info.maeumgagym.core.auth.port.`in`.AppleSignUpUseCase
import com.info.maeumgagym.core.auth.port.out.GenerateJwtPort
import com.info.maeumgagym.core.auth.port.out.ParseAppleTokenPort
import com.info.maeumgagym.core.common.exception.BusinessLogicException
import com.info.maeumgagym.core.user.model.Role
import com.info.maeumgagym.core.user.model.User
import com.info.maeumgagym.core.user.port.out.ExistUserPort
import com.info.maeumgagym.core.user.port.out.RecoveryUserPort
import com.info.maeumgagym.core.user.port.out.SaveUserPort

@UseCase
internal class AppleOAuthService(
    private val saveUserPort: SaveUserPort,
    private val generateJwtPort: GenerateJwtPort,
    private val parseAppleTokenPort: ParseAppleTokenPort,
    private val recoveryUserPort: RecoveryUserPort,
    private val existUserPort: ExistUserPort
) : AppleLoginUseCase, AppleRecoveryUseCase, AppleSignUpUseCase {

    override fun login(token: String): Pair<String, String> {
        // Apple id_token에서 subject값을 받아온다
        val subject = parseAppleTokenPort.parseIdToken(token).subject

        // 존재하지 않는 유저라면 NotFound 예외처리
        if (!existUserPort.existsByOAuthId(subject)) throw BusinessLogicException.USER_NOT_FOUND

        // subject로 토큰 발급 및 반환
        return generateJwtPort.generateTokens(subject)
    }

    override fun signUp(token: String, nickname: String) {
        // nickname 중복 확인
        if (existUserPort.existByNicknameOnWithdrawalSafe(nickname)) throw BusinessLogicException.DUPLICATED_NICKNAME

        // Apple id_token에서 subject값을 받아온다
        val sub = parseAppleTokenPort.parseIdToken(token).subject

        // 중복 유저 확인
        if (existUserPort.existByOAuthIdOnWithdrawalSafe(sub)) throw BusinessLogicException.ALREADY_EXIST_USER

        // 유저 생성
        saveUserPort.save(
            User(
                nickname = nickname,
                roles = mutableListOf(Role.USER),
                oauthId = sub,
                profileImage = null, // 나중에 추가
                physicalInfoModel = null
            )
        )
    }

    override fun recovery(token: String) {
        // Apple에서 id_token을 받아온다
        val response = parseAppleTokenPort.parseIdToken(token)

        // 회원 복구 함수 호출
        recoveryUserPort.recovery(response.subject)
    }
}
