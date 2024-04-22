package com.info.maeumgagym.security.access.checker

/**
 * 주어진 객체의 인증 관련 어노테이션을 기반으로, 요구된 인증에 대해 유효한 사용자인지 확인하는 확인자
 *
 * 인증 관련 어노테이션 목록
 * - [RequireRole][com.info.common.security.RequireRole]
 * - [RequireAuthentication][com.info.common.security.RequireAuthentication]
 * - [Permitted][com.info.common.security.Permitted]
 *
 * @see AbstractAnnotationBasedUserAuthenticationChecker
 *
 * @author Daybreak312
 * @since 20-04-2024
 */
interface AnnotationBasedUserAuthenticationChecker {

    /**
     * @throws [SecurityException][com.info.maeumgagym.common.exception.SecurityException] 유효하지 않은 사용자
     */
    fun check(`object`: Any)
}
