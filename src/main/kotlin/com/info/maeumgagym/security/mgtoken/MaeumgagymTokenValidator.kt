package com.info.maeumgagym.security.mgtoken

import com.info.maeumgagym.security.mgtoken.vo.MaeumgagymToken

/**
 * 복호화되어 [MaeumgagymToken]으로 변환된 토큰을 검증하는 클래스
 *
 * 암호화된 인증용 토큰을 [MaeumgagymTokenDecoder]가 [MaeumgagymToken]으로 변환하면, 이 클래스를 통해 검증한 후 객체를 사용할 것
 *
 * @see MaeumgagymTokenEncoder
 * @see MaeumgagymTokenDecoder
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
interface MaeumgagymTokenValidator {

    /**
     * 복호화된 토큰([MaeumgagymToken])의 유효성을 검증
     *
     * 검증하는 유효성 목록
     * - [tokenId][MaeumgagymToken.tokenId]의 무효화되지 않았는가
     * - 토큰의 [유효 시간 길이][MaeumgagymToken.expireAt]가 [의도한 바][MaeumgagymTokenEncoder.encode]와 같은가
     * - [토큰 유효 기간][MaeumgagymToken.expireAt]을 지나지 않았는가
     * - 토큰 발급 대상과 현재 사용자가 일차하는가: [IP 주소][MaeumgagymToken.ip] 비교
     *
     * @param maeumgagymToken 복호화된 인증용 토큰의 VO
     *
     * @throws com.info.maeumgagym.common.exception.SecurityException 무효한 토큰일 경우
     */
    fun validate(maeumgagymToken: MaeumgagymToken)
}
