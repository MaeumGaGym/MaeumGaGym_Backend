package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.exception.AlreadyExistUserException
import com.info.maeumgagym.user.exception.DuplicatedNicknameException
import com.info.maeumgagym.user.exception.UserNotFoundException
import com.info.maeumgagym.auth.port.`in`.GoogleLoginUseCase
import com.info.maeumgagym.auth.port.`in`.GoogleRecoveryUseCase
import com.info.maeumgagym.auth.port.`in`.GoogleSignupUseCase
import com.info.maeumgagym.auth.port.out.DeleteDeletedAtPort
import com.info.maeumgagym.auth.port.out.GetGoogleInfoPort
import com.info.maeumgagym.auth.port.out.RevokeGoogleTokenPort
import com.info.maeumgagym.user.model.Role
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.*

@UseCase
class GoogleOAuthService(
    private val getGoogleInfoPort: GetGoogleInfoPort,
    private val saveUserPort: SaveUserPort,
    private val existUserByOAuthIdPort: ExistUserByOAuthIdPort,
    private val existUserByNicknamePort: ExistUserByNicknamePort,
    private val generateTokenService: GenerateTokenService,
    private val revokeGoogleTokenPort: RevokeGoogleTokenPort,
    private val recoveryUserPort: RecoveryUserPort
) : GoogleLoginUseCase, GoogleSignupUseCase, GoogleRecoveryUseCase {

    override fun login(accessToken: String): TokenResponse {
        val response = getGoogleInfoPort.getGoogleInfo(accessToken)

        if (!existUserByOAuthIdPort.existsUserByOAuthId(response.sub)) throw UserNotFoundException

        revokeGoogleTokenPort.revokeGoogleToken(accessToken)

        return generateTokenService.execute(response.sub)
    }

    override fun signup(accessToken: String, nickname: String) {
        if (existUserByNicknamePort.existByNicknameInNative(nickname)) throw DuplicatedNicknameException

        val response = getGoogleInfoPort.getGoogleInfo(accessToken)

        if (existUserByOAuthIdPort.existByOAuthIdInNative(response.sub)) throw AlreadyExistUserException

        saveUserPort.saveUser(
            User(
                nickname = nickname,
                roles = mutableListOf(Role.USER),
                oauthId = response.sub,
                profileImage = response.picture
            )
        )
    }

    override fun recovery(accessToken: String) {
        val response = getGoogleInfoPort.getGoogleInfo(accessToken)

        recoveryUserPort.recovery(response.sub)
    }
}
