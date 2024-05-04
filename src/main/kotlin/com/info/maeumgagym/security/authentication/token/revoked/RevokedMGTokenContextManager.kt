package com.info.maeumgagym.security.authentication.token.revoked

import com.info.maeumgagym.security.authentication.token.vo.MaeumgagymToken
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.LocalDateTime

@Component
class RevokedMGTokenContextManager(
    private val revokedMGTokenRepository: RevokedMGTokenRepository
) : RevokedMGTokenContext {

    override fun revoke(token: MaeumgagymToken) {
        revokedMGTokenRepository.save(
            RevokedMGTokenRedisEntity(
                id = token.tokenId,
                ttl = getTtl(token.expireAt)
            )
        )
    }

    override fun checkRevoked(tokenId: String): Boolean =
        revokedMGTokenRepository.findById(tokenId) != null

    private fun getTtl(expireAt: LocalDateTime): Long =
        Duration.between(LocalDateTime.now(), expireAt).seconds + 100
}
