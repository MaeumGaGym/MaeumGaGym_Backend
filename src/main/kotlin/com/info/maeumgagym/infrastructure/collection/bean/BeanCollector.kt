package com.info.maeumgagym.infrastructure.collection.bean

/**
 * 임의의 기준으로 *Spring Bean*을 찾기 위한 인터페이스
 *
 * [ApplicationContext][org.springframework.context.ApplicationContext]의 기능 중 bean 탐색만이 필요할 때, 직접적인 의존을 제거하고 필요한 기능만 사용할 수 있도록 이를 추상화함
 *
 * 하위 타입을 작성해 기준을 특정
 *
 * @sample AnnotationBasedBeanCollector
 *
 * @author Daybreak312
 * @since 09-04-2024
 */
interface BeanCollector<T : Any> {

    /**
     * @param subject 하위 타입에서 결정된 기준의 대상
     * @return 찾은 Bean 이름 : Bean 객체
     */
    fun getBeans(subject: T): Map<String, Any>
}
