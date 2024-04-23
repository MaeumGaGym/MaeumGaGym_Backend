package com.info.maeumgagym.infrastructure.security.access.checker.composite

import com.info.maeumgagym.infrastructure.security.access.checker.AnnotationBasedUserAuthenticationChecker
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

/**
 * 여러 특정 [AnnotationBasedUserAuthenticationChecker]들을 갖고 순회하는 Composite.
 *
 * @author Daybreak312
 * @since 22-04-2024
 */
@Primary
@Component
class AnnotationBasedUserAuthenticationCheckerComposite(
    private val applicationContext: ApplicationContext
) : AnnotationBasedUserAuthenticationChecker {

    private lateinit var annotationBasedUserAuthenticationCheckers: List<AnnotationBasedUserAuthenticationChecker>

    private var initialized: Boolean = false

    private fun init() {
        annotationBasedUserAuthenticationCheckers =
            applicationContext.getBeansOfType(
                AnnotationBasedUserAuthenticationChecker::class.java
            ).values.apply {
                remove(this@AnnotationBasedUserAuthenticationCheckerComposite)
            }.toList()
        initialized = true
    }

    override fun check(`object`: Any) {
        if (!initialized) {
            init()
        }

        annotationBasedUserAuthenticationCheckers.forEach {
            it.check(`object`)
        }
    }
}
