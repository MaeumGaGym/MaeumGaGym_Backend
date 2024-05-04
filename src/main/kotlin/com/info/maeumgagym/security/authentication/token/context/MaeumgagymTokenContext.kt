package com.info.maeumgagym.security.authentication.token.context

import com.info.maeumgagym.security.authentication.token.vo.MaeumgagymToken

/**
 * 현재 요청의 토큰을 반환하는 클래스
 *
 * [RequestContext][com.info.maeumgagym.infrastructure.request.context.CurrentRequestContext]를 통해 현재 Request 객체를 얻어 헤더를 추출하고, [MaeumgagymTokenDecoder][com.info.maeumgagym.security.authentication.token.MaeumgagymTokenDecoder]를 통해 토큰을 복호화
 *
 * @author Daybreak312
 * @since 04-05-2024
 */
interface MaeumgagymTokenContext {

    fun getCurrentToken(): MaeumgagymToken?
}
