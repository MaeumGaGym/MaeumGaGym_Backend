package com.info.maeumgagym.domain.pickle

import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.domain.auth.AuthTestModule.saveInContext
import com.info.maeumgagym.domain.pickle.PickleTestModule.saveInRepository
import com.info.maeumgagym.domain.pickle.entity.PickleJpaEntity
import com.info.maeumgagym.domain.pickle.repository.PickleRepository
import com.info.maeumgagym.domain.user.UserTestModule
import com.info.maeumgagym.domain.user.UserTestModule.saveInRepository
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.error.TestException
import com.info.maeumgagym.pickle.port.`in`.UpdatePickleUseCase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class UpdatePickleServiceTests @Autowired constructor(
    private val pickleRepository: PickleRepository,
    private val updatePickleUseCase: UpdatePickleUseCase,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) {

    private lateinit var pickle: PickleJpaEntity
    private lateinit var user: UserJpaEntity

    @BeforeEach
    fun initialize() {
        user = UserTestModule.createTestUser().saveInRepository(userRepository).saveInContext(userMapper)
        pickle = PickleTestModule.createPickle(user)
    }

    @Test
    fun updatePickle() {
        pickle = pickle.saveInRepository(pickleRepository)
        Assertions.assertDoesNotThrow {
            updatePickleUseCase.updatePickle(
                pickle.videoId,
                PickleTestModule.getUpdatePickleRequest()
            )
        }
        Assertions.assertTrue(
            PickleTestModule.verifyUpdatedPickle(pickle, user)
        )
    }

    @Test
    fun updateNonExistsPickle() {
        TestException.assertThrowsMaeumGaGymExceptionInstance(BusinessLogicException.PICKLE_NOT_FOUND) {
            updatePickleUseCase.updatePickle(
                pickle.videoId,
                PickleTestModule.getUpdatePickleRequest()
            )
        }
        Assertions.assertNull(
            pickleRepository.findById(pickle.videoId)
        )
    }

    @Test
    fun updateOthersPickle() {
        val otherUser = UserTestModule.createOtherUser().saveInRepository(userRepository)
        pickle = PickleTestModule.createPickle(otherUser).saveInRepository(pickleRepository)

        TestException.assertThrowsMaeumGaGymExceptionInstance(SecurityException.PERMISSION_DENIED) {
            updatePickleUseCase.updatePickle(
                pickle.videoId,
                PickleTestModule.getUpdatePickleRequest()
            )
        }
        Assertions.assertFalse(
            PickleTestModule.verifyUpdatedPickle(pickle, otherUser)
        )
    }
}
