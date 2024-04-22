package com.info.maeumgagym.security.access.checker.composite

import com.info.maeumgagym.security.access.checker.AnnotationBasedUserAuthenticationChecker
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class AnnotationBasedUserAuthenticationCheckerComposite(
    private val applicationContext: ApplicationContext
) : AnnotationBasedUserAuthenticationChecker {

    private lateinit var annotationBasedUserAuthenticationCheckers: List<AnnotationBasedUserAuthenticationChecker>

    private var initialized: Boolean = false

    private fun init() {
        annotationBasedUserAuthenticationCheckers =
            applicationContext.getBeansOfType(AnnotationBasedUserAuthenticationChecker::class.java).values.toList()
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
