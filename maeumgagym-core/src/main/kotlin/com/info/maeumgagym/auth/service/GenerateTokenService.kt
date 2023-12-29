package com.info.maeumgagym.auth.service

import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.out.GenerateJwtPort
import org.springframework.stereotype.Component

@Component
class GenerateTokenService(
    private val generateJwtPort: GenerateJwtPort
) {
    fun execute(subject: String): TokenResponse = generateJwtPort.generateTokens(subject)
}
