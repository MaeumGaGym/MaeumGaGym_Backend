package com.info.maeumgagym.domain.user.junit5

import com.info.maeumgagym.adapter.auth.ReadCurrentUserAdapter
import com.info.maeumgagym.domain.auth.AuthTestModule
import com.info.maeumgagym.domain.auth.AuthTestModule.saveInContext
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.module.UserTestModule
import com.info.maeumgagym.domain.user.module.UserTestModule.saveInRepository
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.global.exception.UnAuthorizedException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

/**
 * @see ReadCurrentUserAdapter
 */
@Transactional
@SpringBootTest
class ReadCurrentUserAdapterTests @Autowired constructor(
    private val readCurrentUserAdapter: ReadCurrentUserAdapter,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) {

    private lateinit var user: UserJpaEntity

    @BeforeEach
    fun initialize() {
        user = UserTestModule.createTestUser()
    }

    /**
     * @see ReadCurrentUserAdapter.readCurrentUser
     * @when 성공 상황
     */
    @Test
    fun readCurrentUser() {
        user.saveInRepository(userRepository).saveInContext(userMapper)
        Assertions.assertEquals(
            userMapper.toDomain(user),
            readCurrentUserAdapter.readCurrentUser()
        )
    }

    /**
     * @see ReadCurrentUserAdapter.readCurrentUser
     * @when 실패 상황
     * @success Context가 비어있는 상태에서 currentUser()를 호출해 NPE 발생
     */
    @Test
    fun readCurrentUserWithEmptyContext() {
        user.saveInRepository(userRepository)
        AuthTestModule.clearContext()
        Assertions.assertThrows(NullPointerException::class.java) {
            readCurrentUserAdapter.readCurrentUser()
        }
    }
}
