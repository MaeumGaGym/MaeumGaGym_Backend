package com.info.maeumgagym.global.security.principle

import com.info.maeumgagym.domain.user.exception.UserNotFoundException
import com.info.maeumgagym.user.port.out.FindUserByUUIDPort
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomUserDetailService(
    val findUserByUUIDPort: FindUserByUUIDPort
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = findUserByUUIDPort.findUserById(UUID.fromString(username)) ?: throw UserNotFoundException
        return CustomUserDetails(user)
    }
}
