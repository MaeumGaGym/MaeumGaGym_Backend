package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.exception.AlreadyExistUserException
import com.info.maeumgagym.auth.port.`in`.AppleLoginUseCase
import com.info.maeumgagym.auth.port.`in`.AppleRecoveryUseCase
import com.info.maeumgagym.auth.port.`in`.AppleSignUpUseCase
import com.info.maeumgagym.auth.port.out.DeleteDeletedAtPort
import com.info.maeumgagym.auth.port.out.ParseAppleTokenPort
import com.info.maeumgagym.user.exception.UserNotFoundException
import com.info.maeumgagym.user.model.Role
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.ExistUserByOAuthIdPort
import com.info.maeumgagym.user.port.out.FindDeletedUserByIdPort
import com.info.maeumgagym.user.port.out.SaveUserPort

@UseCase
class AppleOAuthService(
    private val existUserByOAuthIdPort: ExistUserByOAuthIdPort,
    private val saveUserPort: SaveUserPort,
    private val generateTokenService: GenerateTokenService,
    private val parseAppleTokenPort: ParseAppleTokenPort,
    private val findDeletedUserByIdPort: FindDeletedUserByIdPort,
    private val deleteDeletedAtPort: DeleteDeletedAtPort
) : AppleLoginUseCase, AppleRecoveryUseCase, AppleSignUpUseCase {

    override fun login(token: String): TokenResponse {
        val subject = parseAppleTokenPort.parseIdToken(token).subject

        if (!existUserByOAuthIdPort.existsUserByOAuthId(subject)) throw UserNotFoundException

        return generateTokenService.execute(subject)
    }

    override fun signUp(token: String, nickname: String) {
        val idToken = parseAppleTokenPort.parseIdToken(token)

        val sub = idToken.subject

        if (existUserByOAuthIdPort.existByOAuthIdInNative(sub)) throw AlreadyExistUserException

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
        val response = parseAppleTokenPort.parseIdToken(token)

        val deletedUser = findDeletedUserByIdPort.findByIdOrNullInNative(response.subject)
            ?: throw UserNotFoundException

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
