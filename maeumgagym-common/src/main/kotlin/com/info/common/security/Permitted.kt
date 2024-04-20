package com.info.common.security

/**
 * 인증이 필요하지 않은 API임을 명시하기 위한 어노테이션
 *
 * ```
 * // Use like this:
 *
 * @Permitted
 * @RequestMapping("/google")
 * @WebAdapter
 * class GoogleOAuthController { ... }
 *
 * // OR
 *
 * @Permitted
 * @GetMapping
 * fun login() { ... }
 * ```
 *
 * @author Daybreak312
 * @since 20-04-2024
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Permitted
