package com.info.maeumgagym.infrastructure.collector.annotation

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

/**
 * 모든 [AnnotationCollector]들을 순회하는 [AnnotationCollector]
 *
 * @author Daybreak312
 * @since 22-04-2024
 */
@Primary
@Component
class AnnotationCollectorComposite(
    private val applicationContext: ApplicationContext
) : AnnotationCollector {

    private lateinit var collections: List<AnnotationCollector>

    private var initialized: Boolean = false

    private fun init() {
        collections =
            applicationContext.getBeansOfType(
                AnnotationCollector::class.java
            ).values.apply {
                remove(this@AnnotationCollectorComposite)
            }.toList()
        initialized = true
    }

    override fun <A : Annotation> getAnnotationOrNull(`object`: Any, annotation: KClass<A>): A? {
        if (!initialized) {
            init()
        }

        collections.forEach {
            val found = it.getAnnotationOrNull(`object`, annotation)

            if (found != null) {
                return found
            }
        }

        return null
    }
}
