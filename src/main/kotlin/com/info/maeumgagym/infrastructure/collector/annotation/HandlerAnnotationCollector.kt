package com.info.maeumgagym.infrastructure.collector.annotation

import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import java.lang.reflect.Method
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotations

/**
 * Handler(Controller)에 대한 [AnnotationCollector]
 *
 * [HandlerMethod], [Method], [MethodParameter]에 대해 작동
 *
 * @author Daybreak312
 * @since 22-04-2024
 */
@Component
class HandlerAnnotationCollector : AnnotationCollector {

    override fun <A : Annotation> getAnnotationOrNull(`object`: Any, annotation: KClass<A>): A? {
        when (`object`) {
            is HandlerMethod -> {
                return `object`.method.getAnnotation(annotation.java)
                    ?: `object`.bean::class.findAnnotations(annotation).firstOrNull()
            }

            is Method -> {
                return `object`.getAnnotation(annotation.java)
            }

            is MethodParameter -> {
                `object`.parameterAnnotations.forEach {
                    if (it.annotationClass == annotation) {
                        return it as A?
                    }
                }
            }
        }

        return null
    }

    override fun getAnnotations(`object`: Any): List<Annotation> {
        when (`object`) {
            is HandlerMethod -> {
                return if (`object`.method.annotations.isNotEmpty()) {
                    `object`.method.annotations.toList()
                } else {
                    `object`.bean::class.annotations
                }
            }

            is Method -> {
                return `object`.annotations.toList()
            }

            is MethodParameter -> {
                return `object`.parameterAnnotations.toList()
            }
        }

        return emptyList()
    }
}
