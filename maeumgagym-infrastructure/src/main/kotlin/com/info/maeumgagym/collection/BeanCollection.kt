package com.info.maeumgagym.collection

/**
 * 임의의 기준으로 *Spring Bean*을 찾기 위한 인터페이스
 *
 * 하위 타입을 작성해 기준을 특정
 *
 * @author Daybreak312
 * @since 09-04-2024
 */
interface BeanCollection<T : Any> {

    /**
     * @param subject 하위 타입에서 결정된 기준의 대상
     * @return 찾은 Bean 이름 : Bean 객체
     */
    fun getBeans(subject: T): Map<String, Any>
}
