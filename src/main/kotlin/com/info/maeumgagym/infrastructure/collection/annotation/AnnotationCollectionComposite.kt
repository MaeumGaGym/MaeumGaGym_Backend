package com.info.maeumgagym.infrastructure.collection.annotation

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

/**
 * 모든 [AnnotationCollection]들을 순회하는 [AnnotationCollection]
 *
 * @author Daybreak312
 * @since 22-04-2024
 */
@Primary
@Component
class AnnotationCollectionComposite(
    private val applicationContext: ApplicationContext
) : AnnotationCollection {

    private lateinit var collections: List<AnnotationCollection>

    private var initialized: Boolean = false

    private fun init() {
        collections =
            applicationContext.getBeansOfType(
                AnnotationCollection::class.java
            ).values.apply {
                remove(this@AnnotationCollectionComposite)
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
