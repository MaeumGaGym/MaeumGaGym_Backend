package com.info.maeumgagym.security.access.checker.impl

import com.info.common.security.RequireAuthentication
import com.info.maeumgagym.security.access.checker.AbstractAnnotationBasedUserAuthenticationChecker
import org.springframework.stereotype.Component

/**
 * [@RequireAuthentication][RequireAuthentication]에 대한 인증 확인자
 *
 * @author Daybreak312
 * @since 21-04-2024
 */
@Component
class RequireAuthenticationChecker : AbstractAnnotationBasedUserAuthenticationChecker() {

    override fun check(`object`: Any) {
        `object`.getAnnotationOrNull(RequireAuthentication::class) ?: return

        checkInvalidAuthentication()
    }
}
