package com.info.maeumgagym.security.mgtoken.revoked

import com.info.maeumgagym.common.exception.CriticalException
import com.info.maeumgagym.security.mgtoken.vo.MaeumgagymToken
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.LocalDateTime

@Component
class UsableMGTokenContextManager(
    private val usableMGTokenRepository: UsableMGTokenRepository
) : UsableMGTokenContext {

    override fun saveToken(token: MaeumgagymToken) {
        if (token.expireAt.isBefore(LocalDateTime.now())) {
            throw CriticalException("Expired token got at UsableMGTokenContext.")
        }

        usableMGTokenRepository.save(
            UsableMGTokenRedisEntity(
                id = token.tokenId,
                ttl = getTtl(token.expireAt)
            )
        )
    }

    override fun revoke(token: MaeumgagymToken) {
        usableMGTokenRepository.deleteById(token.tokenId)
    }

    override fun isNotUsable(tokenId: String): Boolean =
        usableMGTokenRepository.findById(tokenId) == null

    private fun getTtl(expireAt: LocalDateTime): Long =
        Duration.between(LocalDateTime.now(), expireAt).seconds
}
