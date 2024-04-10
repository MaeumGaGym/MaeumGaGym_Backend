package com.info.common.security

/**
 * [@WebAdapter][com.info.common.WebAdapter] 어노테이션이 부착된 컨트롤러 단위로, Role 인증이 필요함을 명시하기 위한 어노테이션
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
