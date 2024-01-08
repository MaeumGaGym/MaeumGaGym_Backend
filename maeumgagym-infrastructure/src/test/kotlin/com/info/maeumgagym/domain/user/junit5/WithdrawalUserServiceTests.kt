package com.info.maeumgagym.domain.user.junit5

import com.info.maeumgagym.adapter.auth.ReadCurrentUserAdapter
import com.info.maeumgagym.auth.service.WithdrawalUserService
import com.info.maeumgagym.domain.auth.AuthTestModule.saveInContext
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.module.UserTestModule
import com.info.maeumgagym.domain.user.module.UserTestModule.saveInRepository
import com.info.maeumgagym.domain.user.repository.DeletedAtRepository
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.global.exception.UnAuthorizedException
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
    private val withdrawalUserService: WithdrawalUserService,
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

    /**
     * @see WithdrawalUserService.withdrawal
     * @when 실패 상황
     * @success 이미 탈퇴한 유저이기 때문에 readCurrentUser()에서 UnAuthorizedException 발생
     *  실제 플로우상, 탈퇴한 유저는 토큰을 발급할 수 없으므로 해당 상황은 존재할 수 없음
     *  @see ReadCurrentUserAdapter.readCurrentUser
     * @fail DeletedAtJpaEntity를 찾고, 예외를 발생시키는 로직이 존재하는지 확인
     */
    @Test
    fun withdrawalAlreadyDidUser() {
        withdrawalUserService.withdrawal()
        Assertions.assertThrows(UnAuthorizedException::class.java) {
            withdrawalUserService.withdrawal()
        }
    }
}
