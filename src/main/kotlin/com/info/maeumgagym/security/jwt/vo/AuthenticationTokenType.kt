package com.info.maeumgagym.security.jwt.vo

/**
 * [AuthenticationToken] 및 그 외 임의의 토큰에서 타입을 표기하기 위한 enum
 *
 * @param value 토큰 암호화 시에 토큰의 용도를 명시하기 위해 사용
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
enum class AuthenticationTokenType {
    ACCESS_TOKEN,
    REFRESH_TOKEN
}
