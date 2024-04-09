package com.info.common.security

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class NeedRole(
    val role: String = "ADMIN"
)
