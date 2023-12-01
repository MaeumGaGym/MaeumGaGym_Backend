package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.`in`.AppleLoginUseCase
import com.info.maeumgagym.auth.port.out.*
import com.info.maeumgagym.user.model.Role
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.FindUserByOAuthIdPort
import com.info.maeumgagym.user.port.out.SaveUserPort
import org.springframework.transaction.annotation.Transactional

@UseCase
class AppleLoginService(
    private val appleJwtParsePort: AppleJwtParsePort,
    private val generatePublicKeyPort: GeneratePublicKeyPort,
    private val parsePublicKeyPort: ParsePublicKeyPort,
    private val readApplePublicKey: ReadApplePublicKeyPort,
    private val findUserByOAuthIdPort: FindUserByOAuthIdPort,
    private val saveUserPort: SaveUserPort,
    private val generateJwtPort: GenerateJwtPort
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

        return generateJwtPort.generateToken(user.id.toString())
    }

    private fun parseIdToken(token: String) = parsePublicKeyPort.parseClaimsFromPublicKey(
        token,
        generatePublicKeyPort.generatePublicKey(
            appleJwtParsePort.parseHeaders(token),
            readApplePublicKey.readPublicKey()
        )
    )
}
