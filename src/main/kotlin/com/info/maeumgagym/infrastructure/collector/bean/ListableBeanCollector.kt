package com.info.maeumgagym.infrastructure.collector.bean

import kotlin.reflect.KClass

/**
 * 동일한 상위 타입을 가진 여러 빈을 [컬렉션 프레임워크][Collection]로 불러오기 위한 수집기
 *
 * @author Daybreak312
 * @since 19-08-2024
 */
interface ListableBeanCollector {

    fun <T : Any> getBeansOfType(type: KClass<T>): List<T>

    fun <T: Any> getBeansMapOfType(type: KClass<T>): Map<String, T>
}
