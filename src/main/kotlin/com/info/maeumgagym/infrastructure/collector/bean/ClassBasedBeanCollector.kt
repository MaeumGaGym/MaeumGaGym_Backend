package com.info.maeumgagym.infrastructure.collector.bean

import kotlin.reflect.KClass

/**
 * 특정 타입의 스프링 빈을 찾는 수집기
 *
 * [ApplicationContext][org.springframework.context.ApplicationContext]의 기능 중 bean 탐색만이 필요할 때, 직접적인 의존을 제거하고 필요한 기능만 사용할 수 있도록 이를 추상화함
 *
 * @author Daybreak312
 * @since 11-07-2024
 */
interface ClassBasedBeanCollector {

    /**
     * 특정 타입에 대한 빈 수집
     *
     * @param T 탐색할 빈의 타입
     * @param subject [T]의 [Reflection][KClass]
     * @return 빈 이름과 빈 객체가 이어진 [Map]
     */
    fun <T : Any> getBeans(subject: KClass<T>): Map<String, T>
}
