package com.info.maeumgagym.security.authentication.vo

import com.info.maeumgagym.common.exception.CriticalException
import com.info.maeumgagym.user.model.User
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority

/**
 * [User]를 가지고 있는 [Authentication]
 *
 * @author Daybreak312
 * @since 02-04-2024
 */
class UserModelAuthentication(
    private val userSubject: String,
    private val user: User? = null
) : Authentication {

    /**
     * 기본 생성자의 몸체, [userSubject]와 [user]의 subject가 일치하는지 확인
     */
    init {
        if (user != null && user.oauthId != userSubject) {
            throw CriticalException(500, "user's subject and userSubject are different")
        }
    }

    /**
     * [getPrincipal]과 같은 기능을 함
     */
    override fun getName(): String {
        return userSubject
    }

    /**
     * [user]가 내장되어 있다면 [User.roles]를, 아니라면 빈 Collection 반환
     */
    override fun getAuthorities(): List<SimpleGrantedAuthority> {
        return user?.roles?.map {
            SimpleGrantedAuthority(it.name)
        } ?: listOf()
    }

    /**
     * 사용되지 않는 기능
     */
    override fun getCredentials(): Any? = null

    /**
     * 사용되지 않는 기능
     */
    override fun getDetails(): Any? = null

    override fun getPrincipal(): Any = this.userSubject

    /**
     * 인가용 객체이므로, 늘 인증되어 있음
     */
    override fun isAuthenticated(): Boolean = true

    /**
     * 비인증 상태로 전환할 수 없음
     */
    override fun setAuthenticated(isAuthenticated: Boolean) {
        if (!isAuthenticated) {
            throw CriticalException(500, "UserModelAuthentication MUSE BE Authenticated.")
        }
    }
}
