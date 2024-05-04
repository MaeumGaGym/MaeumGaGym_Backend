package com.info.maeumgagym.infrastructure.request.context

import com.info.maeumgagym.security.authentication.token.vo.MaeumgagymToken
import javax.servlet.http.HttpServletRequest

/**
 * 현재 요청에 대한 Request 객체를 얻어오기 위한 모듈
 *
 * [RequestContextInterceptor][com.info.maeumgagym.infrastructure.request.interceptor.RequestContextInterceptor]에 의해 [setCurrentRequest]가 호출되어 객체가 초기화됨.
 *
 * @see com.info.maeumgagym.infrastructure.request.interceptor.RequestContextInterceptor
 *
 * @author Daybreak312
 * @since 02-05-2024
 */
interface RequestContext {

    fun setCurrentRequest(request: HttpServletRequest)

    fun getCurrentRequest(): HttpServletRequest

    fun getCurrentToken(): MaeumgagymToken?
}
