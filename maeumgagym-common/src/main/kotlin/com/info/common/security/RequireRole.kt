package com.info.common.security

/**
 * [Role][com.info.maeumgagym.user.model.Role] 인증이 필요한 API임을 명시하기 위한 어노테이션
 *
 * ```
 * // Use like this:
 *
 * @RequireRole
 * @GetMapping
 * fun report() { ... }
 *
 * // And, Use annotation like this:
 *
 * @RequireRole // == @RequireRole("ADMIN")
 *
 * // OR
 *
 * @RequireRole("ADMIN")
 *
 * // OR
 *
 * @RequireRole("USER")
 * ```
 *
 * @param role [Role][com.info.maeumgagym.user.model.Role]의 문자열 이름
 *
 * @author Daybreak312
 * @since 10-04-2024
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequireRole(
    val role: String = "ADMIN"
)
