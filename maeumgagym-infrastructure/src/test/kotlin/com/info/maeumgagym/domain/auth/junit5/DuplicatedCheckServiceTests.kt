package com.info.maeumgagym.domain.auth.junit5

import com.info.maeumgagym.auth.port.`in`.DuplicatedNicknameCheckUseCase
import com.info.maeumgagym.domain.user.module.UserTestModule
import com.info.maeumgagym.domain.user.module.UserTestModule.saveInRepository
import com.info.maeumgagym.domain.user.repository.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class DuplicatedCheckServiceTests @Autowired constructor(
    private val duplicatedCheckService: DuplicatedNicknameCheckUseCase,
    private val userRepository: UserRepository
) {

    /**
     * @see DuplicatedCheckService.existByNickname
     * @when 성공 상황
     * @fail 다중 책임을 가졌던 Query 문이 수정되었는지 확인
     * @fail UserJpaEntity가 정상적으로 저장되는지 확인
     * @fail nickname이 UserJpaEntity에 정상적으로 주입되는지 확인
     */
    @Test
    fun checkExistsUserNickname() {
        UserTestModule.createTestUser().saveInRepository(userRepository)
        userRepository.delete(UserTestModule.createOtherUser().saveInRepository(userRepository))
        Assertions.assertTrue(duplicatedCheckService.existByNickname(UserTestModule.TEST_USER_NICKNAME))
        Assertions.assertTrue(duplicatedCheckService.existByNickname(UserTestModule.OTHER_USER_NICKNAME))
    }

    /**
     * @see DuplicatedCheckService.existByNickname
     * @when 성공 상황
     * @fail 다중 책임을 가졌던 Query 문이 수정되었는지 확인
     */
    @Test
    fun checkNonExistsUserNickname() {
        Assertions.assertFalse(duplicatedCheckService.existByNickname(UserTestModule.TEST_USER_NICKNAME + "a"))
    }
}
