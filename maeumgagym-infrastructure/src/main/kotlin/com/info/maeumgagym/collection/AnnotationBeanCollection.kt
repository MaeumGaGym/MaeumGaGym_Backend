package com.info.maeumgagym.collection

import kotlin.reflect.KClass

/**
 * 특정 어노테이션이 붙어 있는 *Spring Bean*을 찾기 위한 클래스
 *
 * @author Daybreak312
 * @since 09-04-2024
 */
interface AnnotationBeanCollection : BeanCollection<KClass<Annotation>> {

    /**
     * @param subject 기준 삼을 어노테이션
     * @return 찾은 Bean 이름 : Bean 객체
     */
    override fun getBeans(subject: KClass<Annotation>): Map<String, Any>
}
