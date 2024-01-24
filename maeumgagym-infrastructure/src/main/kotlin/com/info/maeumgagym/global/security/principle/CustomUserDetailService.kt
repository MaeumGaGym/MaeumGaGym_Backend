package com.info.maeumgagym.global.security.principle

import com.info.maeumgagym.user.exception.UserNotFoundException
import com.info.maeumgagym.user.port.out.ReadUserPort
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CustomUserDetailService(
    private val readUserPort: ReadUserPort
) : UserDetailsService {

    // 유저를 DB에서 불러와 UserDetails를 반환하는 함수
    override fun loadUserByUsername(username: String?): CustomUserDetails {
        // user를 전달 받은 UK값으로 불러오기
        val user = readUserPort.readByOAuthId(username!!) ?: throw UserNotFoundException

        // CustomUserDetails로 형변환 후 반환
        return CustomUserDetails(user)
    }
}
