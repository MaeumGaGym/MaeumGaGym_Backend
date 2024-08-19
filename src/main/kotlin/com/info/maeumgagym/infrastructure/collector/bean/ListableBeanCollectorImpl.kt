package com.info.maeumgagym.infrastructure.collector.bean

import okhttp3.internal.toImmutableMap
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class ListableBeanCollectorImpl(
    private val applicationContext: ApplicationContext
) : ListableBeanCollector {

    override fun <T : Any> getBeansOfType(type: KClass<T>): List<T> =
        applicationContext.getBeansOfType(type.java).values.toList()

    override fun <T : Any> getBeansMapOfType(type: KClass<T>): Map<String, T> =
        applicationContext.getBeansOfType(type.java).toImmutableMap()
}
