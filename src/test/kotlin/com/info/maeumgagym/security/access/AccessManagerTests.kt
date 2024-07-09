package com.info.maeumgagym.security.access

import com.info.maeumgagym.application.domain.user.mapper.UserMapper
import com.info.maeumgagym.application.domain.user.repository.UserRepository
import com.info.maeumgagym.common.annotation.security.RequireAuthentication
import com.info.maeumgagym.common.annotation.security.RequireRole
import com.info.maeumgagym.common.exception.AuthenticationException
import com.info.maeumgagym.core.user.model.Role
import com.info.maeumgagym.core.user.model.User
import com.info.maeumgagym.security.access.manager.AccessManager
import com.info.maeumgagym.security.authentication.provider.AuthenticationManager
import com.info.maeumgagym.util.UserTestUtils
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.method.HandlerMethod
import java.lang.reflect.Method

@Transactional
@SpringBootTest
class AccessManagerTests @Autowired constructor(
    private val accessManager: AccessManager,
    private val authenticationManager: AuthenticationManager,

    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) {

    lateinit var handlerMethod: HandlerMethod

    fun init(
        onFindRequireRole: RequireRole?,
        onFIndRequireAuthentication: RequireAuthentication?
    ) {
        handlerMethod = mockk<HandlerMethod>()

        val method = mockk<Method>()
        every { method.getAnnotation(RequireAuthentication::class.java) } returns onFIndRequireAuthentication
        every { method.getAnnotation(RequireRole::class.java) } returns onFindRequireRole

        every { handlerMethod.method } returns method
        every { handlerMethod.bean } returns method

        authenticationManager.clear()
    }

    @Test
    fun 인증이_불필요한_API에_인증하지_않은_상태로_접근() {
        init(null, null)

        Assertions.assertDoesNotThrow {
            accessManager.checkAccessAllowed(
                handlerMethod
            )
        }
    }

    @Test
    fun 인증이_불필요한_API에_인증한_상태로_접근() {
        init(null, null)

        insertUserInRepository(
            UserTestUtils.TEST_USER
        )

        Assertions.assertDoesNotThrow {
            accessManager.checkAccessAllowed(
                handlerMethod
            )
        }
    }

    @Test
    fun 인증이_필요한_API에_인증하지_않은_상태로_접근() {
        init(null, RequireAuthentication())

        Assertions.assertThrows(AuthenticationException::class.java) {
            accessManager.checkAccessAllowed(
                handlerMethod
            )
        }
    }

    @Test
    fun 인증이_필요한_API에_인증한_상태로_접근() {
        init(null, RequireAuthentication())

        insertUserInRepository(
            UserTestUtils.TEST_USER
        )

        Assertions.assertDoesNotThrow {
            accessManager.checkAccessAllowed(
                handlerMethod
            )
        }
    }

    @Test
    fun ADMIN_권한이_필요한_API에_인증하지_않은_상태로_접근() {
        init(RequireRole(Role.ADMIN), null)

        Assertions.assertThrows(AuthenticationException::class.java) {
            accessManager.checkAccessAllowed(
                handlerMethod
            )
        }
    }

    @Test
    fun ADMIN_권한이_필요한_API에_인증한_상태로_접근() {
        init(RequireRole(Role.ADMIN), null)

        insertUserInRepository(
            UserTestUtils.TEST_USER
        )

        Assertions.assertThrows(AuthenticationException::class.java) {
            accessManager.checkAccessAllowed(
                handlerMethod
            )
        }
    }

    @Test
    fun ADMIN_권한이_필요한_API에_권한이_있는_상태로_접근() {
        init(RequireRole(Role.ADMIN), null)

        insertUserInRepository(
            UserTestUtils.ADMIN_USER
        )

        Assertions.assertDoesNotThrow {
            accessManager.checkAccessAllowed(
                handlerMethod
            )
        }
    }

    private fun insertUserInRepository(user: User) {
        userRepository.save(
            userMapper.toEntity(user)
        )
        authenticationManager.setAuthentication(UserTestUtils.TEST_USER.oauthId)
    }
}
