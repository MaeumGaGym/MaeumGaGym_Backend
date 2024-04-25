package com.info.maeumgagym.infrastructure.collection.bean.impl

import com.info.maeumgagym.infrastructure.collection.bean.AnnotationBeanCollection
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
open class AnnotationBeanCollectionImpl(
    private val applicationContext: ApplicationContext
) : AnnotationBeanCollection {

    override fun getBeans(subject: KClass<Annotation>): Map<String, Any> =
        applicationContext.getBeansWithAnnotation(subject.java)
}
