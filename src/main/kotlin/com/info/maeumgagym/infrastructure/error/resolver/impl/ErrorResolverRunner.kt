package com.info.maeumgagym.infrastructure.error.resolver.impl

import com.info.maeumgagym.infrastructure.collector.bean.ClassBasedBeanCollector
import com.info.maeumgagym.infrastructure.error.resolver.ErrorResolver
import com.info.maeumgagym.infrastructure.error.vo.ErrorInfo
import org.springframework.stereotype.Component

@Component
class ErrorResolverRunner(
    private val classBasedBeanCollector: ClassBasedBeanCollector
) : ErrorResolver {

    private lateinit var errorResolvers: List<ErrorResolver>

    private var initialized = false

    private fun init() {
        if (!initialized) {
            errorResolvers =
                classBasedBeanCollector.getBeans(ErrorResolver::class)
                    .filter { it.value != this }
                    .map { it.value }
            initialized = true
        }
    }

    override fun resolve(errorInfo: ErrorInfo) {
        init()

        errorResolvers.forEach { it.resolve(errorInfo) }
    }
}
