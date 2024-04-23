package com.info.maeumgagym.infrastructure.security.access.manager

import com.info.maeumgagym.core.common.exception.SecurityException

/**
 * 매핑된 핸들러에 대하여 사용자가 접근이 허가 되었는지 확인하는 관리자
 *
 * @author Daybreak312
 * @since 20-04-2024
 */
interface AccessManager {

    /**
     * 요청에 대한 접근이 허가되었는지 확인
     *
     * 여러 [AnnotationBasedUserAuthenticationChecker][com.info.maeumgagym.security.access.checker.AnnotationBasedUserAuthenticationChecker]들을 순회하며 접근 허가 여부를 확인
     *
     * @throws SecurityException 접근 거부
     */
    @Throws(SecurityException::class)
    fun checkAccessAllowed(handler: Any)
}
