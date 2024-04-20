package com.info.maeumgagym.security.access.checker

import org.springframework.web.method.HandlerMethod
import javax.servlet.http.HttpServletRequest

/**
 * [AccessManager][com.info.maeumgagym.security.access.manager.AccessManager]보다 더 좁은 범위로, 요청의 접근 허가 여부를 판단하는 확인자
 *
 * @author Daybreak312
 * @since 20-04-2024
 */
interface AccessAllowedChecker {

    fun check(request: HttpServletRequest, handler: HandlerMethod)
}
