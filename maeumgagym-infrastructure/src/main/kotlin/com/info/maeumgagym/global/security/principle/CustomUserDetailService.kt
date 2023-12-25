package com.info.maeumgagym.global.security.principle

import com.info.maeumgagym.user.exception.UserNotFoundException
import com.info.maeumgagym.user.port.out.FindUserByOAuthIdPort
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CustomUserDetailService(
    val findUserByOAuthIdPort: FindUserByOAuthIdPort
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = findUserByOAuthIdPort.findUserByOAuthId(username!!) ?: throw UserNotFoundException
        return CustomUserDetails(user)
    }
}
