package com.info.maeumgagym.collection.annotation

import kotlin.reflect.KClass

interface AnnotationCollection {

    fun <A : Annotation> getAnnotationOrNull(`object`: Any, annotation: KClass<A>): A?
}
