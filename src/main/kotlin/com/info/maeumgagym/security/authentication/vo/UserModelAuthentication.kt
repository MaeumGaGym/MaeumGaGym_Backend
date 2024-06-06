package com.info.maeumgagym.security.authentication.vo

import com.info.maeumgagym.common.exception.CriticalException
import com.info.maeumgagym.core.user.model.User
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority

/**
 * [User]를 가지고 있는 [Authentication]
 *
 * [User]는 생성 당시에는 null일 수 있지만, 접근하는 시점에서는 무조건 값이 존재해야함
 *
 * @constructor [userSubject]와 [user]의 subject가 일치하는지 확인
 *
 * @author Daybreak312
 * @since 02-04-2024
 */
class UserModelAuthentication(
    private val userSubject: String,
    private val user: User? = null
) : Authentication {

    fun getUser(): User = this.user!!

    init {
        if (user != null && user.oauthId != userSubject) {
            throw CriticalException("user's subject and userSubject are different")
        }
    }

    /**
     * [getPrincipal]과 같은 기능을 하며, [userSubject]와 같음.
     */
    override fun getName(): String {
        return userSubject
    }

    /**
     * [User.roles]를 [SimpleGrantedAuthority]로 변환해 반환
     */
    override fun getAuthorities(): List<SimpleGrantedAuthority> {
        return getUser().roles.map {
            SimpleGrantedAuthority(it.name)
        }
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
            throw CriticalException("UserModelAuthentication MUSE BE Authenticated.")
        }
    }

    /**
     * [user]가 로드되었는지의 여부 반환
     */
    fun isUserLoaded(): Boolean = user != null
}
