package com.info.maeumgagym.domain.pickle

import com.info.maeumgagym.domain.auth.AuthTestModule.saveInContext
import com.info.maeumgagym.domain.pickle.PickleTestModule.saveInRepository
import com.info.maeumgagym.domain.pickle.entity.PickleJpaEntity
import com.info.maeumgagym.domain.pickle.repository.PickleRepository
import com.info.maeumgagym.domain.user.UserTestModule
import com.info.maeumgagym.domain.user.UserTestModule.saveInRepository
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.pickle.exception.AlreadyExistPickleException
import com.info.maeumgagym.pickle.port.`in`.CreatePickleUseCase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class UploadPickleServiceTests @Autowired constructor(
    private val pickleRepository: PickleRepository,
    private val createPickleUseCase: CreatePickleUseCase,
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
    fun uploadPickle() {
        Assertions.assertDoesNotThrow {
            createPickleUseCase.createPickle(
                PickleTestModule.getUploadPickleRequest(pickle.videoId)
            )
        }
        Assertions.assertNotNull(
            pickleRepository.findById(pickle.videoId)
        )
    }

    @Test
    fun uploadPickleWithExistsVideoId() {
        pickle = pickle.saveInRepository(pickleRepository)
        Assertions.assertThrows(AlreadyExistPickleException::class.java) {
            createPickleUseCase.createPickle(
                PickleTestModule.getUploadPickleRequest(pickle.videoId)
            )
        }
    }
}
