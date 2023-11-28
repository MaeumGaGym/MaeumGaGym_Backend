package com.info.maeumgagym.auth.port.out

import com.info.maeumgagym.auth.dto.response.TokenResponse
import java.util.UUID

interface GenerateJwtPort {
    fun generateToken(userId: UUID): TokenResponse
}
