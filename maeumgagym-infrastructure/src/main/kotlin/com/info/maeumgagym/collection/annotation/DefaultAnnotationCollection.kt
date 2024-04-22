package com.info.maeumgagym.collection.annotation

import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotations

@Component
class DefaultAnnotationCollection : AnnotationCollection {

    override fun <A : Annotation> getAnnotationOrNull(`object`: Any, annotation: KClass<A>): A? =
        `object`::class.findAnnotations(annotation).firstOrNull()
}

