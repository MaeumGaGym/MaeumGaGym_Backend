package com.info.maeumgagym.security.access.manager

import com.info.maeumgagym.security.access.checker.AnnotationBasedUserAuthenticationChecker
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod

@Component
class AccessManagerImpl(
    private val annotationBasedUserAuthenticationChecker: AnnotationBasedUserAuthenticationChecker
) : AccessManager {

    override fun checkAccessAllowed(handler: Any) {
        // 매핑된 핸들러, 정확힌 매핑된 메소드를 HandlerMethod 타입으로 캐스팅
        val handlerMethod = castHandlerToHandlerMethodOrNull(handler) ?: return

        // Controller 클래스 자체의 어노테이션 확인
        annotationBasedUserAuthenticationChecker.check(handlerMethod.bean)
        // 매핑된 메소드에 부착된 어노테이션 확인
        annotationBasedUserAuthenticationChecker.check(handlerMethod)
    }

    private fun castHandlerToHandlerMethodOrNull(handler: Any): HandlerMethod? {
        if (handler !is HandlerMethod) {
            return null
        }

        return handler
    }
}
