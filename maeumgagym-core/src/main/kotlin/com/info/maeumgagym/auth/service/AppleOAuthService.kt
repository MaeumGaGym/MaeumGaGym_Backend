package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.exception.AlreadyExistUserException
import com.info.maeumgagym.auth.port.`in`.AppleLoginUseCase
import com.info.maeumgagym.auth.port.`in`.AppleRecoveryUseCase
import com.info.maeumgagym.auth.port.`in`.AppleSignUpUseCase
import com.info.maeumgagym.auth.port.out.GenerateJwtPort
import com.info.maeumgagym.auth.port.out.ParseAppleTokenPort
import com.info.maeumgagym.user.exception.DuplicatedNicknameException
import com.info.maeumgagym.user.exception.UserNotFoundException
import com.info.maeumgagym.user.model.Role
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.ExistUserByNicknamePort
import com.info.maeumgagym.user.port.out.ExistUserByOAuthIdPort
import com.info.maeumgagym.user.port.out.RecoveryUserPort
import com.info.maeumgagym.user.port.out.SaveUserPort
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@UseCase
@Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [Exception::class])
internal class AppleOAuthService(
    private val existUserByOAuthIdPort: ExistUserByOAuthIdPort,
    private val saveUserPort: SaveUserPort,
    private val generateJwtPort: GenerateJwtPort,
    private val parseAppleTokenPort: ParseAppleTokenPort,
    private val recoveryUserPort: RecoveryUserPort,
    private val existUserByNicknamePort: ExistUserByNicknamePort
) : AppleLoginUseCase, AppleRecoveryUseCase, AppleSignUpUseCase {

    override fun login(token: String): TokenResponse {
        // Apple id_token에서 subject값을 받아온다
        val subject = parseAppleTokenPort.parseIdToken(token).subject

        // 존재하지 않는 유저라면 NotFound 예외처리
        if (!existUserByOAuthIdPort.existsUserByOAuthId(subject)) throw UserNotFoundException

        // subject로 토큰 발급 및 반환
        return generateJwtPort.generateTokens(subject)
    }

    override fun signUp(token: String, nickname: String) {
        // nickname 중복 확인
        if (existUserByNicknamePort.existByNicknameOfWithdrawalSafe(nickname)) throw DuplicatedNicknameException

        // Apple id_token에서 subject값을 받아온다
        val sub = parseAppleTokenPort.parseIdToken(token).subject

        // 중복 유저 확인
        if (existUserByOAuthIdPort.existByOAuthIdOfWithdrawalSafe(sub)) throw AlreadyExistUserException

        // 유저 생성
        saveUserPort.saveUser(
            User(
                nickname = nickname,
                roles = mutableListOf(Role.USER),
                oauthId = sub,
                profileImage = null
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
