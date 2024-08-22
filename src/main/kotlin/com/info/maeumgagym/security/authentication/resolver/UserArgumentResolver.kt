package com.info.maeumgagym.security.authentication.resolver

import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.user.model.User
import com.info.maeumgagym.infrastructure.processor.annotation.WebAdapterMethodArgumentAnnotationProcessor
import com.info.maeumgagym.security.authentication.checker.AnnotationBasedAuthenticationChecker
import org.springframework.stereotype.Component

@Component
class UserArgumentResolver(
    private val annotationBasedAuthenticationChecker: AnnotationBasedAuthenticationChecker,
    private val readCurrentUserPort: ReadCurrentUserPort
) : WebAdapterMethodArgumentAnnotationProcessor {

    override fun <T> supports(type: Class<T>, annotations: List<Annotation>): Boolean =
        type == User::class.java

    override fun <T> processing(type: Class<T>, annotations: List<Annotation>): T {

        if (type != User::class.java) {
            throw RuntimeException()
        }

        annotationBasedAuthenticationChecker.checkAuthentication(annotations)

        return readCurrentUserPort.readCurrentUser() as T
    }
}
