package com.info.maeumgagym.domain.user

import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.global.security.principle.CustomUserDetails
import com.info.maeumgagym.user.model.Role
import com.info.maeumgagym.user.model.User
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.time.LocalDateTime

internal object UserTestModule {

    const val TEST_USER_NICKNAME = "테스트 유저 닉네임"
    const val TEST_USER_OAUTH_ID = "testUserOAuthId"
    const val TEST_PROFILE_IMAGE =
        "https://lh3.googleusercontent.com/a/ACg8ocJqPiMvxq0HiLHPr4_mDgUMvp2RZaeWZpOnvM3_90E9=s360-c-no"
    val TEST_USER_ROLES = listOf(Role.USER)
        get() = field.toMutableList()
    val TEST_USER_WAKA = null

    const val OTHER_USER_NICKNAME = "다른 유저 닉네임"
    const val OTHER_USER_OAUTH_ID = "otherUserOAuthId"
    const val OTHER_PROFILE_IMAGE =
        "https://lh3.googleusercontent.com/a/ACg8ocJqPiMvxq0HiLHPr4_mDgUMvp2RZaeWZpOnvM3_90E9=s360-c-no"
    val OTHER_USER_ROLES = listOf(Role.USER)
        get() = field.toMutableList()
    val OTHER_USER_WAKA = LocalDateTime.of(2024, 1, 15, 14, 30)

    fun createTestUser(): UserJpaEntity =
        UserJpaEntity(
            nickname = TEST_USER_NICKNAME,
            oauthId = TEST_USER_OAUTH_ID,
            profileImage = TEST_PROFILE_IMAGE,
            roles = TEST_USER_ROLES.toMutableList(),
            wakaStartedAt = TEST_USER_WAKA
        )

    fun createOtherUser(): UserJpaEntity =
        UserJpaEntity(
            nickname = OTHER_USER_NICKNAME,
            oauthId = OTHER_USER_OAUTH_ID,
            profileImage = OTHER_PROFILE_IMAGE,
            roles = OTHER_USER_ROLES.toMutableList(),
            wakaStartedAt = OTHER_USER_WAKA
        )

    fun UserJpaEntity.saveInRepository(userRepository: UserRepository): UserJpaEntity =
        userRepository.save(this)

    fun User.saveInContext(): User =
        apply {
            SecurityContextHolder.getContext().authentication =
                CustomUserDetails(this).run {
                    UsernamePasswordAuthenticationToken(this, null, this.authorities)
                }
        }
}
