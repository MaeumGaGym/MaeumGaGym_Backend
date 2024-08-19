package com.info.maeumgagym.infrastructure.processor.annotation

import com.info.maeumgagym.infrastructure.collector.annotation.AnnotationCollector
import com.info.maeumgagym.infrastructure.collector.bean.ListableBeanCollector
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class WebAdapterMethodArgumentAnnotationResolver(
    private val listableBeanCollector: ListableBeanCollector,
    private val annotationCollector: AnnotationCollector
) : HandlerMethodArgumentResolver {

    private lateinit var processors: List<WebAdapterMethodArgumentAnnotationProcessor>

    private fun initialize() {
        if (!this::processors.isInitialized) {
            this.processors =
                listableBeanCollector.getBeansOfType(WebAdapterMethodArgumentAnnotationProcessor::class)
        }
    }

    override fun supportsParameter(parameter: MethodParameter) = true

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {

        val annotations = annotationCollector.getAnnotations(parameter)

        processors.forEach {
            if (it.supports(parameter.parameterType, annotations)) {
                return it.processing(parameter.parameterType, annotations)
            }
        }

        return null
    }
}
