package com.info.maeumgagym.security.authentication.checker

interface AnnotationBasedAuthenticationChecker {

    fun checkAuthentication(annotations: List<Annotation>)
}
