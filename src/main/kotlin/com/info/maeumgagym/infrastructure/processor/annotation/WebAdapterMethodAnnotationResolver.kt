package com.info.maeumgagym.infrastructure.processor.annotation

import com.info.maeumgagym.infrastructure.collector.annotation.AnnotationCollector
import com.info.maeumgagym.infrastructure.collector.bean.ListableBeanCollector
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class WebAdapterMethodAnnotationResolver(
    private val annotationCollector: AnnotationCollector,
    private val listableBeanCollector: ListableBeanCollector
) : HandlerInterceptor {

    private lateinit var processors: List<WebAdapterMethodAnnotationProcessor>

    private fun initialize() {
        if (!this::processors.isInitialized) {
            this.processors =
                listableBeanCollector.getBeansOfType(WebAdapterMethodAnnotationProcessor::class)
        }
    }

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        initialize()

        if (handler !is HandlerMethod) {
            return true
        }

        val annotations = annotationCollector.getAnnotations(handler)

        this.processors.forEach {
            if (it.supports(annotations)) {
                it.preProcessing(annotations)
            }
        }

        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        if (handler !is HandlerMethod) {
            return
        }

        val annotations = annotationCollector.getAnnotations(handler)

        this.processors.forEach {
            if (it.supports(annotations)) {
                it.postProcessing(annotations)
            }
        }
    }
}
