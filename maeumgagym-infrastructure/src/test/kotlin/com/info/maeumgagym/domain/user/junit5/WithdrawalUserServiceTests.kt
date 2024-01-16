package com.info.maeumgagym.domain.user.junit5

import com.info.maeumgagym.auth.port.`in`.WithdrawalUserUseCase
import com.info.maeumgagym.domain.auth.AuthTestModule.saveInContext
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.module.UserTestModule
import com.info.maeumgagym.domain.user.module.UserTestModule.saveInRepository
import com.info.maeumgagym.domain.user.repository.DeletedAtRepository
import com.info.maeumgagym.domain.user.repository.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class WithdrawalUserServiceTests @Autowired constructor(
    private val withdrawalUserService: WithdrawalUserUseCase,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val deletedAtRepository: DeletedAtRepository
) {

    private lateinit var user: UserJpaEntity

    @BeforeEach
    fun initialize() {
        user = UserTestModule.createTestUser().saveInRepository(userRepository).saveInContext(userMapper)
    }

    /**
     * @see WithdrawalUserService.withdrawal
     * @when 성공 상황
     * @fail UserJpaEntity의 isDeleted가 정상적으로 수정되는지 확인
     * @fail UserRepository.findById의 Query 문에  WHEN isDeleted == false가 정상적으로 적용되는지 확인
     * @fail DeletedAtJpaEntity가 정상적으로 생성되는지 확인
     * @fail DeletedAtJpaEntity가 정상적으로 저장되는지 확인
     */
    @Test
    fun withdrawalUser() {
        Assertions.assertDoesNotThrow {
            withdrawalUserService.withdrawal()
        }
        Assertions.assertNull(userRepository.findByIdOrNull(user.id!!))
        Assertions.assertNotNull(deletedAtRepository.findByIdOrNull(user.id!!))
    }
}
