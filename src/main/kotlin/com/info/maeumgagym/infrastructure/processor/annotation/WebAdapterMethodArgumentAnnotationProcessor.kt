package com.info.maeumgagym.infrastructure.processor.annotation

interface WebAdapterMethodArgumentAnnotationProcessor {

    fun <T : Any?> supports(type: Class<T>, annotations: List<Annotation>): Boolean

    fun <T : Any?> processing(type: Class<T>, annotations: List<Annotation>): T
}
