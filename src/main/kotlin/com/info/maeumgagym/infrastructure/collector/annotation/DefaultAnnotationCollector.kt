package com.info.maeumgagym.infrastructure.collector.annotation

import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotations

/**
 * 가장 기본적인 [AnnotationCollector]
 *
 * 클래스의 리플렉션으로부터 어노테이션 추출
 *
 * @author Daybreak312
 * @since 22-04-2024
 */
@Component
class DefaultAnnotationCollector : AnnotationCollector {

    override fun <A : Annotation> getAnnotationOrNull(`object`: Any, annotation: KClass<A>): A? =
        `object`::class.findAnnotations(annotation).firstOrNull()

    override fun getAnnotations(`object`: Any): List<Annotation> =
        `object`::class.annotations
}
