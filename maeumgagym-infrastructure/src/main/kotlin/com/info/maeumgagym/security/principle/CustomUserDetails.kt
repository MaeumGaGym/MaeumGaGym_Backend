package com.info.maeumgagym.security.principle

import com.info.maeumgagym.user.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private var user: User?,
    private val oauthId: String
) : UserDetails {

    fun getUser(): User? = user

    fun fillUser(user: User) {
        if (this.user == null) {
            this.user = user
        }
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        user?.roles?.map {
            SimpleGrantedAuthority(it.name)
        }?.toMutableList() ?: mutableListOf()

    override fun getPassword(): String? = null

    override fun getUsername(): String = oauthId

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
