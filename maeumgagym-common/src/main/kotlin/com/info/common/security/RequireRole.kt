package com.info.common.security

/**
 * [@WebAdapter][com.info.common.responsibility.WebAdapter] 어노테이션이 부착된 컨트롤러 단위로, [Role][com.info.maeumgagym.user.model.Role] 인증이 필요함을 명시하기 위한 어노테이션
 *
 * ```
 * // Use like this:
 *
 * @RequireRole
 * @RequestMapping("/report")
 * @WebAdapter
 * class ReportController { ... }
 *
 * // OR
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
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequireRole(
    val role: String = "ADMIN"
)
