package com.info.maeumgagym.global.security.principle

import com.info.maeumgagym.user.port.out.FindUserPort
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomUserDetailService(
    val findUserPort: FindUserPort
): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = findUserPort.findUserById(UUID.fromString(username))
        return CustomUserDetails(user)
    }
}