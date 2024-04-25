package com.info.maeumgagym.infrastructure.collection.bean

import kotlin.reflect.KClass

/**
 * 특정 어노테이션이 붙어 있는 *Spring Bean*을 찾기 위한 클래스
 *
 * @see BeanCollector
 *
 * @author Daybreak312
 * @since 09-04-2024
 */
interface AnnotationBasedBeanCollector : BeanCollector<KClass<Annotation>> {

    /**
     * @param subject 기준 삼을 어노테이션의 리플렉션
     * @return 찾은 Bean 이름 : Bean 객체
     */
    override fun getBeans(subject: KClass<Annotation>): Map<String, Any>
}
