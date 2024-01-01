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
import com.info.maeumgagym.user.port.out.ExistUserByNicknamePort
import com.info.maeumgagym.user.port.out.ExistUserByOAuthIdPort
import com.info.maeumgagym.user.port.out.FindDeletedUserByIdPort
import com.info.maeumgagym.user.port.out.SaveUserPort

@UseCase
class GoogleOAuthService(
    private val getGoogleInfoPort: GetGoogleInfoPort,
    private val saveUserPort: SaveUserPort,
    private val existUserByOAuthIdPort: ExistUserByOAuthIdPort,
    private val existUserByNicknamePort: ExistUserByNicknamePort,
    private val generateTokenService: GenerateTokenService,
    private val revokeGoogleTokenPort: RevokeGoogleTokenPort,
    private val findDeletedUserByIdPort: FindDeletedUserByIdPort,
    private val deleteDeletedAtPort: DeleteDeletedAtPort
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

        val deletedUser = findDeletedUserByIdPort.findByIdOrNullInNative(response.sub) ?: throw UserNotFoundException

        deletedUser.apply {
            saveUserPort.saveUser(
                User(
                    id = id,
                    nickname = nickname,
                    roles = roles,
                    oauthId = oauthId,
                    profileImage = profileImage,
                    isDeleted = false
                )
            )
        }

        deleteDeletedAtPort.delete(deletedUser.id)
    }
}
