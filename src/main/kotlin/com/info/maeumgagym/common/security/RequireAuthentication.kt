package com.info.maeumgagym.common.security

/**
 * 권한에 상관 없이 인증이 필요한 API임을 명시하기 위한 어노테이션
 *
 * ```
 * // Use like this:
 *
 * @RequireAuthentication
 * @GetMapping
 * fun createPurpose(...) { ... }
 * ```
 * @author Daybreak312
 * @since 20-04-2024
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequireAuthentication
