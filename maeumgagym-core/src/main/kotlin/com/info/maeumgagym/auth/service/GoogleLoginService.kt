package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.`in`.GoogleLoginUseCase
import com.info.maeumgagym.auth.port.out.GenerateJwtPort
import com.info.maeumgagym.auth.port.out.GetGoogleInfoPort
import com.info.maeumgagym.user.model.Role
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.SaveUserPort
import com.info.maeumgagym.user.port.out.FindUserByOAuthIdPort

@UseCase
class GoogleLoginService(
    private val getGoogleInfoPort: GetGoogleInfoPort,
    private val saveUserPort: SaveUserPort,
    private val findUserByOAuthIdPort: FindUserByOAuthIdPort,
    private val generateJwtPort: GenerateJwtPort
) : GoogleLoginUseCase {

    override fun googleLogin(accessToken: String): TokenResponse {
        val googleInfoResponse = getGoogleInfoPort.getGoogleInfo(accessToken)

        val user = findUserByOAuthIdPort.findUserByOAuthId(googleInfoResponse.sub) ?: saveUserPort.saveUser(
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
