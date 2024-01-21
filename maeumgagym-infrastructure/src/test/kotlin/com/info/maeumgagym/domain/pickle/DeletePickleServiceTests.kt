package com.info.maeumgagym.domain.pickle

import com.info.maeumgagym.auth.exception.PermissionDeniedException
import com.info.maeumgagym.domain.auth.AuthTestModule.saveInContext
import com.info.maeumgagym.domain.pickle.PickleTestModule.saveInRepository
import com.info.maeumgagym.domain.pickle.entity.PickleJpaEntity
import com.info.maeumgagym.domain.pickle.repository.PickleRepository
import com.info.maeumgagym.domain.user.UserTestModule
import com.info.maeumgagym.domain.user.UserTestModule.saveInRepository
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.pickle.exception.PickleNotFoundException
import com.info.maeumgagym.pickle.port.`in`.PickleDeleteUseCase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class DeletePickleServiceTests @Autowired constructor(
    private val pickleRepository: PickleRepository,
    private val deletePickleUseCase: PickleDeleteUseCase,
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
    fun deletePickle() {
        pickle = pickle.saveInRepository(pickleRepository)
        Assertions.assertDoesNotThrow {
            deletePickleUseCase.deletePickle(pickle.videoId)
        }
        Assertions.assertNull(
            pickleRepository.findByIdOrNull(pickle.videoId)
        )
    }

    @Test
    fun deleteNonExistsPickle() {
        Assertions.assertNull(
            pickleRepository.findByIdOrNull(pickle.videoId)
        )
        Assertions.assertThrows(PickleNotFoundException::class.java) {
            deletePickleUseCase.deletePickle(pickle.videoId)
        }
        Assertions.assertNull(
            pickleRepository.findByIdOrNull(pickle.videoId)
        )
    }

    @Test
    fun deleteOthersPickle() {
        val otherUser = UserTestModule.createOtherUser().saveInRepository(userRepository)
        pickle = PickleTestModule.createPickle(otherUser).saveInRepository(pickleRepository)

        Assertions.assertThrows(PermissionDeniedException::class.java) {
            deletePickleUseCase.deletePickle(
                pickle.videoId
            )
        }
        Assertions.assertNotNull(
            pickleRepository.findByIdOrNull(pickle.videoId)
        )
    }
}
