package com.info.maeumgagym.security.jwt.vo

import com.info.maeumgagym.common.exception.CriticalException

/**
 * [Jwt] 및 그 외 임의의 Jwt 토큰의 타입을 표기하기 위한 enum
 *
 * @param value Jwt 토큰 암호화 시에 헤더에서 사용
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
enum class JwtType(
    val value: String
) {
    ACCESS_TOKEN("access"),
    REFRESH_TOKEN("refresh");

    companion object {
        fun of(string: String) =
            when (string) {
                ACCESS_TOKEN.value -> ACCESS_TOKEN

                REFRESH_TOKEN.value -> REFRESH_TOKEN

                else -> throw CriticalException(500, "Wrong Jwt Type")
            }
    }
}
