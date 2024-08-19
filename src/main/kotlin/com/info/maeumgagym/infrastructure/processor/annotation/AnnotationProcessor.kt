package com.info.maeumgagym.infrastructure.processor.annotation

interface AnnotationProcessor {

    fun supports(annotations: List<Annotation>)

    fun postProcessing(annotations: List<Annotation>)

    fun preProcessing(annotations: List<Annotation>)
}
