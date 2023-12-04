package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.`in`.AppleLoginUseCase
import com.info.maeumgagym.auth.port.out.AppleJwtParsePort
import com.info.maeumgagym.auth.port.out.GeneratePublicKeyPort
import com.info.maeumgagym.auth.port.out.GetJwtBodyPort
import com.info.maeumgagym.auth.port.out.ReadApplePublicKeyPort
import com.info.maeumgagym.user.model.Role
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.FindUserByOAuthIdPort
import com.info.maeumgagym.user.port.out.SaveUserPort
import org.springframework.transaction.annotation.Transactional

@UseCase
class AppleLoginService(
    private val appleJwtParsePort: AppleJwtParsePort,
    private val generatePublicKeyPort: GeneratePublicKeyPort,
    private val getJwtBodyPort: GetJwtBodyPort,
    private val readApplePublicKey: ReadApplePublicKeyPort,
    private val findUserByOAuthIdPort: FindUserByOAuthIdPort,
    private val saveUserPort: SaveUserPort,
    private val generateTokenService: GenerateTokenService
) : AppleLoginUseCase {

    @Transactional
    override fun execute(token: String): TokenResponse {
        val idToken = parseIdToken(token)

        val sub = idToken.subject

        val user: User = findUserByOAuthIdPort.findUserByOAuthId(sub) ?: saveUserPort.saveUser(
            User(
                nickname = idToken.get("name", String::class.java),
                roles = mutableListOf(Role.USER),
                oauthId = sub,
                profilePath = null
            )
        )

        return generateTokenService.execute(user.id.toString())
    }

    private fun parseIdToken(token: String) = getJwtBodyPort.getJwtBody(
        token,
        generatePublicKeyPort.generatePublicKey(
            appleJwtParsePort.parseHeaders(token),
            readApplePublicKey.readPublicKey()
        )
    )
}
