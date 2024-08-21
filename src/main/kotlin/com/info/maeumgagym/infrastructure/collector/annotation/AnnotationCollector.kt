package com.info.maeumgagym.infrastructure.collector.annotation

import kotlin.reflect.KClass

/**
 * 주어진 객체로부터 요구되는 타입의 어노테이션을 추출
 *
 * 객체별로 어노테이션을 추출하는 방법이 상이하여 추가됨
 *
 * 기본 구현체는 [AnnotationCollectorComposite]로, 이를 통해 하위 구현체들을 모두 순회
 *
 * @sample [com.info.maeumgagym.security.authentication.checker.RequireRoleChecker.preProcessing]
 *
 * @see AnnotationCollectorComposite
 *
 * @author Daybreak312
 * @since 22-04-2024
 */
interface AnnotationCollector {

    /**
     * @param `object` 임의의 객체
     * @param annotation 추출할 어노테이션의 타입([KClass])
     *
     * @return 찾은 Annotation, 없을 경우 null 반환
     */
    fun <A : Annotation> getAnnotationOrNull(`object`: Any, annotation: KClass<A>): A?

    fun getAnnotations(`object`: Any): List<Annotation>
}
