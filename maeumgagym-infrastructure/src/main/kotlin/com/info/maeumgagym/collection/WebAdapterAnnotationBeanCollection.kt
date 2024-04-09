package com.info.maeumgagym.collection

/**
 * [@WebAdapter][com.info.common.WebAdapter] 어노테이션 부착된 Bean들을 탐색
 *
 * @author Daybreak312
 * @since 09-04-2024
 */
interface WebAdapterAnnotationBeanCollection : AnnotationBeanCollection {

    /**
     * @return [@WebAdapter][com.info.common.WebAdapter] 어노테이션이 부착된 Bean 이름 : Bean 객체
     */
    fun getBeans(): Map<String, Any>
}
