package com.info.maeumgagym.security.access.manager

import com.info.maeumgagym.security.access.checker.AccessAllowedChecker
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import javax.servlet.http.HttpServletRequest

@Component
class AccessManagerImpl(
    private val applicationContext: ApplicationContext
) : AccessManager {

    private lateinit var accessAllowedCheckers: List<AccessAllowedChecker>

    private var initilized: Boolean = false

    private fun init() {
        accessAllowedCheckers = applicationContext.getBeansOfType(AccessAllowedChecker::class.java).values.toList()
        initilized = true
    }

    override fun checkAccessAllowed(request: HttpServletRequest, handler: Any) {
        if (!initilized) {
            init()
        }

        val handlerMethod = castHandlerToHandlerMethodOrNull(handler) ?: return

        accessAllowedCheckers.forEach {
            it.check(request, handlerMethod)
        }
    }

    private fun castHandlerToHandlerMethodOrNull(handler: Any): HandlerMethod? {
        if (handler !is HandlerMethod) {
            return null
        }

        return handler
    }
}
