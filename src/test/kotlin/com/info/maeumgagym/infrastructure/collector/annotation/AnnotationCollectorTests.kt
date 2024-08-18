package com.info.maeumgagym.infrastructure.collector.annotation

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class AnnotationCollectorTests @Autowired constructor(
    private val annotationCollector: AnnotationCollector
) {

    @Test
    fun 존재하는_어노테이션_탐색() {
        Assertions.assertNotNull(
            annotationCollector.getAnnotationOrNull(
                OneAnnotationTestClass(),
                TestAnnotation1::class
            )
        )
    }

    @Test
    fun 존재하지_않는_어노테이션_탐색() {
        Assertions.assertNull(
            annotationCollector.getAnnotationOrNull(
                NoAnnotationTestClass(),
                TestAnnotation1::class
            )
        )
    }

    @Test
    fun 여러_개의_어노테이션이_부착된_클래스에서_존재하는_어노테이션_탐색() {
        Assertions.assertNotNull(
            annotationCollector.getAnnotationOrNull(
                OneTwoAnnotationTestClass(),
                TestAnnotation1::class
            )
        )

        Assertions.assertNotNull(
            annotationCollector.getAnnotationOrNull(
                OneTwoAnnotationTestClass(),
                TestAnnotation2::class
            )
        )
    }

    @Test
    fun 여러_개의_어노테이션이_부착된_클래스에서_존재하지_않는_어노테이션_탐색() {
        Assertions.assertNull(
            annotationCollector.getAnnotationOrNull(
                OneTwoAnnotationTestClass(),
                TestAnnotation3::class
            )
        )
    }

    private annotation class TestAnnotation1
    private annotation class TestAnnotation2
    private annotation class TestAnnotation3

    private class NoAnnotationTestClass

    @TestAnnotation1
    private class OneAnnotationTestClass

    @TestAnnotation1
    @TestAnnotation2
    private class OneTwoAnnotationTestClass
}
