package com.info.maeumgagym.common.annotation.security

/**
 * 인증이 필요하지 않은 API임을 명시하기 위한 어노테이션
 *
 * ```
 * // Use like this:
 *
 * @Permitted
 * @GetMapping
 * fun login() { ... }
 * ```
 *
 * @author Daybreak312
 * @since 20-04-2024
 */
@[
    Target(AnnotationTarget.FUNCTION)
    Retention(AnnotationRetention.RUNTIME)
]
annotation class Permitted
