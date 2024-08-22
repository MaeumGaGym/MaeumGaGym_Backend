package com.info.maeumgagym.infrastructure.processor.annotation

interface WebAdapterMethodAnnotationProcessor {

    fun preProcessing(annotations: List<Annotation>)

    fun postProcessing(annotations: List<Annotation>)
}
