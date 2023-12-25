package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.dto.request.SignupRequest
import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.exception.AlreadyExistUserException
import com.info.maeumgagym.user.exception.DuplicatedNicknameException
import com.info.maeumgagym.user.exception.UserNotFoundException
import com.info.maeumgagym.auth.port.`in`.GoogleLoginUseCase
import com.info.maeumgagym.auth.port.`in`.GoogleSignupUseCase
import com.info.maeumgagym.auth.port.out.GetGoogleInfoPort
import com.info.maeumgagym.user.model.Role
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.ExistUserByNicknamePort
import com.info.maeumgagym.user.port.out.ExistUserByOAuthIdPort
import com.info.maeumgagym.user.port.out.SaveUserPort

@UseCase
class GoogleOAuthService(
    private val getGoogleInfoPort: GetGoogleInfoPort,
    private val saveUserPort: SaveUserPort,
    private val existUserByOAuthIdPort: ExistUserByOAuthIdPort,
    private val existUserByNicknamePort: ExistUserByNicknamePort,
    private val generateTokenService: GenerateTokenService
) : GoogleLoginUseCase, GoogleSignupUseCase {

    override fun login(accessToken: String): TokenResponse {
        val googleInfoResponse = getGoogleInfoPort.getGoogleInfo(accessToken)
        if (!existUserByOAuthIdPort.existByOAuthId(googleInfoResponse.sub)) throw UserNotFoundException
        return generateTokenService.execute(googleInfoResponse.sub)
    }

    override fun signup(accessToken: String, signupRequest: SignupRequest) {
        if (existUserByNicknamePort.existByNickname(signupRequest.nickname)) throw DuplicatedNicknameException
        val googleInfoResponse = getGoogleInfoPort.getGoogleInfo(accessToken)
        if (existUserByOAuthIdPort.existByOAuthId(googleInfoResponse.sub)) throw AlreadyExistUserException
        saveUserPort.saveUser(
            User(
                nickname = signupRequest.nickname,
                roles = mutableListOf(Role.USER),
                oauthId = googleInfoResponse.sub,
                profileImage = googleInfoResponse.picture
            )
        )
    }
}
