package com.info.maeumgagym.auth.service

import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.`in`.GoogleLoginUseCase
import com.info.maeumgagym.auth.port.out.GenerateJwtPort
import com.info.maeumgagym.auth.port.out.GetGoogleAccessTokenPort
import com.info.maeumgagym.auth.port.out.GetGoogleInfoPort
import com.info.maeumgagym.user.model.Role
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.CreateUserPort
import com.info.maeumgagym.user.port.out.FindUserByOAuthIdPort
import org.springframework.stereotype.Service

@Service
class GoogleLoginService(
    private val getGoogleAccessTokenPort: GetGoogleAccessTokenPort,
    private val getGoogleInfoPort: GetGoogleInfoPort,
    private val createUserPort: CreateUserPort,
    private val findUserByOAuthIdPort: FindUserByOAuthIdPort,
    private val generateJwtPort: GenerateJwtPort
) : GoogleLoginUseCase {

    override fun googleLogin(code: String): TokenResponse {
        val googleTokenResponse = getGoogleAccessTokenPort.getGoogleAccessToken(code)
        val googleInfoResponse = getGoogleInfoPort.getGoogleInfo(googleTokenResponse.accessToken)

        val user = findUserByOAuthIdPort.findUserByOAuthId(googleInfoResponse.sub) ?: createUserPort.createUser(
            User(
                nickname = googleInfoResponse.name,
                roles = mutableListOf(Role.USER),
                oauthId = googleInfoResponse.sub,
                profilePath = googleInfoResponse.picture
            )
        )

        return generateJwtPort.generateToken(user.id)
    }
}
