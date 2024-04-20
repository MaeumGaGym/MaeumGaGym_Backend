package com.info.maeumgagym.security.access.manager

import com.info.maeumgagym.common.exception.SecurityException
import javax.servlet.http.HttpServletRequest

/**
 * 요청에 대한 접근이 허가 여부를 확인하는 관리자
 *
 * @author Daybreak312
 * @since 20-04-2024
 */
interface AccessManager {

    /**
     * 요청에 대한 접근이 허가되었는지 확인
     *
     * 여러 [AccessAllowedChecker][com.info.maeumgagym.security.access.checker.AccessAllowedChecker]들을 순회하며 접근 허가 여부를 확인
     *
     * @throws SecurityException 접근이 거부된 경우의 Exception
     */
    @Throws(SecurityException::class)
    fun checkAccessAllowed(request: HttpServletRequest, handler: Any)
}
