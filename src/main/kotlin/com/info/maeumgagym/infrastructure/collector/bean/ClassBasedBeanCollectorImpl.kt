package com.info.maeumgagym.infrastructure.collector.bean

import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class ClassBasedBeanCollectorImpl(
    private val applicationContext: ApplicationContext
) : ClassBasedBeanCollector {

    override fun <T : Any> getBeans(subject: KClass<T>): Map<String, T> {
        return applicationContext.getBeansOfType(subject.java)
    }
}
