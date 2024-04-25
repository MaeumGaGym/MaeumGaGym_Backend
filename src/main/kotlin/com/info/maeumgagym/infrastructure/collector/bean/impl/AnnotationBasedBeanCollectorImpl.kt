package com.info.maeumgagym.infrastructure.collector.bean.impl

import com.info.maeumgagym.infrastructure.collector.bean.AnnotationBasedBeanCollector
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
open class AnnotationBasedBeanCollectorImpl(
    private val applicationContext: ApplicationContext
) : AnnotationBasedBeanCollector {

    override fun getBeans(subject: KClass<Annotation>): Map<String, Any> =
        applicationContext.getBeansWithAnnotation(subject.java)
}
