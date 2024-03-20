package com.info.maeumgagym.security.principle

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CustomUserDetailService : UserDetailsService {

    // 유저를 DB에서 불러와 UserDetails를 반환하는 함수
    override fun loadUserByUsername(username: String): CustomUserDetails {
        // CustomUserDetails에 User를 null로 삽입해 반환
        return CustomUserDetails(null, username)
    }
}
