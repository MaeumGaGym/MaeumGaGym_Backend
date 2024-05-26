package com.info.maeumgagym.common.annotation.security

import com.info.maeumgagym.core.user.model.Role

/**
 * [Role][com.info.maeumgagym.core.user.model.Role] 인증이 필요한 API임을 명시하기 위한 어노테이션
 *
 * ```
 * // Use like this:
 *
 * @RequireRole // Default Value, Role.ADMIN
 * @GetMapping
 * fun report() { ... }
 *
 * // And, Use annotation like this:
 *
 * @RequireRole // Default Value, Role.ADMIN)
 *
 * // OR
 *
 * @RequireRole(Role.ADMIN)
 *
 * // OR
 *
 * @RequireRole(Role.USER)
 * ```
 *
 * @param role [Role][com.info.maeumgagym.core.user.model.Role]
 *
 * @author Daybreak312
 * @since 10-04-2024
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequireRole(
    val role: Role = Role.ADMIN
)
