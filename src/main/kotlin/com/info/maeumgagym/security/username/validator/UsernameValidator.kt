package com.info.maeumgagym.security.username.validator

/**
 * 주어진 username이 실제로 존재하는지 확인
 *
 * @author Daybreak312
 * @since 19-06-2024
 */
interface UsernameValidator {

    operator fun invoke(username: String): Boolean
}
