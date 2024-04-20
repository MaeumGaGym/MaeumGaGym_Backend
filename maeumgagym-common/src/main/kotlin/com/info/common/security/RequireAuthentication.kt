package com.info.common.security

/**
 * 권한에 상관 없이 인증이 필요한 API임을 명시하기 위한 어노테이션
 *
 * ```
 * // Use like this:
 *
 * @RequireAuthentication
 * @RequestMapping("/purpose")
 * @WebAdapter
 * class PurposeController { ... }
 *
 * // OR
 *
 * @RequireAuthentication
 * @GetMapping
 * fun createPurpose(...) { ... }
 * ```
 * @author Daybreak312
 * @since 20-04-2024
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequireAuthentication
