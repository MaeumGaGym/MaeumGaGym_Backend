package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.exception.AlreadyWithdrawalUserException
import com.info.maeumgagym.auth.port.`in`.AppleLoginUseCase
import com.info.maeumgagym.auth.port.out.*
import com.info.maeumgagym.user.model.Role
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.FindUserByOAuthIdPort
import com.info.maeumgagym.user.port.out.SaveUserPort
import org.springframework.transaction.annotation.Transactional

@UseCase
class AppleLoginService(
    private val findUserByOAuthIdPort: FindUserByOAuthIdPort,
    private val saveUserPort: SaveUserPort,
    private val generateTokenService: GenerateTokenService,
    private val parseAppleTokenPort: ParseAppleTokenPort
) : AppleLoginUseCase {

    @Transactional
    override fun execute(token: String): TokenResponse {
        val idToken = parseAppleTokenPort.parseIdToken(token)

        val sub = idToken.subject

        val user: User = findUserByOAuthIdPort.findUserByOAuthId(sub)?.let {
            if (it.isDeleted) {
                throw AlreadyWithdrawalUserException
            } else {
                return@let it
            }
        } ?: saveUserPort.saveUser(
            User(
                nickname = idToken.get("name", String::class.java),
                roles = mutableListOf(Role.USER),
                oauthId = sub,
                profileImage = null
            )
        )

        return generateTokenService.execute(user.id.toString())
    }
}
