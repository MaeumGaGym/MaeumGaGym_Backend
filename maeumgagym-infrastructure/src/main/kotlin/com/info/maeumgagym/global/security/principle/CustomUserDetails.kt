package com.info.maeumgagym.global.security.principle

import com.info.maeumgagym.user.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private val user: User
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authList: MutableList<SimpleGrantedAuthority> = mutableListOf()
        user.roles.forEach { authList.add(SimpleGrantedAuthority(it.name)) }
        return authList
    }

    override fun getPassword(): String? = null

    override fun getUsername(): String = user.id.toString()

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
