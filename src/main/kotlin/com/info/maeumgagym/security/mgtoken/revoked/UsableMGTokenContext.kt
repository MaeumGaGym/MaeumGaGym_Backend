package com.info.maeumgagym.security.mgtoken.revoked

import com.info.maeumgagym.security.mgtoken.vo.MaeumgagymToken

/**
 * 사용 가능한 토큰의 [id][MaeumgagymToken.tokenId]을 모아둔 컨텍스트
 *
 * DAO에 대한 직접 접근을 제거하여 고수준에서 데이터베이스 정보를 관리하기 위한 고수준 추상화.
 *
 * [Redis][com.info.maeumgagym.security.mgtoken.revoked.UsableMGTokenRepository]를 통해 관리하며, [저장되는 시간][com.info.maeumgagym.security.mgtoken.revoked.UsableMGTokenRedisEntity.ttl]은 토큰의 남은 유효 시간(초).
 *
 * @see UsableMGTokenRepository
 * @see UsableMGTokenRedisEntity
 *
 * @author Daybreak312
 * @since 04-05-2024
 */
interface UsableMGTokenContext {

    /**
     * 토큰을 새로 등록
     */
    fun saveToken(token: MaeumgagymToken)

    /**
     * 토큰을 무효화 처리
     *
     * 이미 [Redis][com.info.maeumgagym.security.mgtoken.revoked.UsableMGTokenRepository]에 저장되어 있는 [토큰 id][MaeumgagymToken.tokenId]를 제거
     */
    fun revoke(token: MaeumgagymToken)

    /**
     * 토큰의 사용 가능 여부 반환
     */
    fun isNotUsable(tokenId: String): Boolean
}
