package com.info.maeumgagym.collection.annotation

import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import java.lang.reflect.Method
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotations

@Component
class HandlerAnnotationCollection : AnnotationCollection {

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
}
