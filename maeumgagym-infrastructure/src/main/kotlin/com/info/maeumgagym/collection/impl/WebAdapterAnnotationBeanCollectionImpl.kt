package com.info.maeumgagym.collection.impl

import com.info.common.WebAdapter
import com.info.maeumgagym.collection.WebAdapterAnnotationBeanCollection
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class WebAdapterAnnotationBeanCollectionImpl(
    private val applicationContext: ApplicationContext
) : WebAdapterAnnotationBeanCollection, AnnotationBeanCollectionImpl(applicationContext) {

    override fun getBeans(): Map<String, Any> =
        applicationContext.getBeansWithAnnotation(WebAdapter::class.java)
}
