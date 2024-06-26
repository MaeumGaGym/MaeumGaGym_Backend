package com.info.maeumgagym.security.mgtoken.revoked

import com.info.maeumgagym.security.mgtoken.vo.MaeumgagymToken

/**
 * 무효화된 토큰의 [id][MaeumgagymToken.tokenId]을 모아둔 컨텍스트
 *
 * DAO에 대한 직접 접근을 제거하여 고수준에서 데이터베이스 정보를 관리하기 위한 고수준 추상화.
 *
 * [Redis][com.info.maeumgagym.security.mgtoken.revoked.RevokedMGTokenRepository]를 통해 관리하며, [저장되는 시간][com.info.maeumgagym.security.mgtoken.revoked.RevokedMGTokenRedisEntity.ttl]은 토큰의 남은 유효 시간(초) + 100초.
 *
 * @see RevokedMGTokenRepository
 * @see RevokedMGTokenRedisEntity
 *
 * @author Daybreak312
 * @since 04-05-2024
 */
interface RevokedMGTokenContext {

    /**
     * 토큰을 무효화 처리
     *
     * [토큰 id][MaeumgagymToken.tokenId]를 [Redis][com.info.maeumgagym.security.mgtoken.revoked.RevokedMGTokenRepository]에 저장
     */
    fun revoke(token: MaeumgagymToken)

    /**
     * 토큰이 무효화되었는지 확인
     *
     * 토큰이 만료되었을 경우, 정확히는 [Redis][com.info.maeumgagym.security.mgtoken.revoked.RevokedMGTokenRepository]에서 삭제되었을 경우 무효화되지 않은 것으로 처리됨
     */
    fun checkRevoked(tokenId: String): Boolean
}
