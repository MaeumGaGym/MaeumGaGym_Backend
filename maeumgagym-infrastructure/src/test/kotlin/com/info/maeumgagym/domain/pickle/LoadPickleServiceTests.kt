package com.info.maeumgagym.domain.pickle

import com.info.maeumgagym.domain.auth.AuthTestModule.saveInContext
import com.info.maeumgagym.domain.pickle.PickleTestModule.saveInRepository
import com.info.maeumgagym.domain.pickle.entity.PickleJpaEntity
import com.info.maeumgagym.domain.pickle.mapper.PickleMapper
import com.info.maeumgagym.domain.pickle.repository.PickleRepository
import com.info.maeumgagym.domain.user.UserTestModule
import com.info.maeumgagym.domain.user.UserTestModule.saveInRepository
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.pickle.dto.response.PickleResponse
import com.info.maeumgagym.pickle.exception.PickleNotFoundException
import com.info.maeumgagym.pickle.exception.ThereNoPicklesException
import com.info.maeumgagym.pickle.model.Pickle.Companion.toResponse
import com.info.maeumgagym.pickle.port.`in`.LoadPickleFromIdUseCase
import com.info.maeumgagym.pickle.port.`in`.LoadPicklesFromPoseUseCase
import com.info.maeumgagym.pickle.port.`in`.LoadRecommendationPicklesUseCase
import com.info.maeumgagym.pickle.port.out.GenerateM3u8URLPort
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class LoadPickleServiceTests @Autowired constructor(
    private val loadRecommendationPicklesUseCase: LoadRecommendationPicklesUseCase,
    private val loadPickleFromIdUseCase: LoadPickleFromIdUseCase,
    private val loadPicklesFromPoseUseCase: LoadPicklesFromPoseUseCase,
    private val pickleRepository: PickleRepository,
    private val pickleMapper: PickleMapper,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val generateM3u8URLPort: GenerateM3u8URLPort
) {

    private lateinit var user: UserJpaEntity
    private lateinit var pickleList: List<PickleJpaEntity>

    @BeforeEach
    fun initialize() {
        user = UserTestModule.createTestUser().saveInRepository(userRepository).saveInContext(userMapper)
        val pickleMutableList = mutableListOf<PickleJpaEntity>()
        for (i in 1..10) {
            pickleMutableList.add(
                PickleTestModule.createPickle(user).saveInRepository(pickleRepository)
            )
        }
        pickleList = pickleMutableList
    }

    @RepeatedTest(10)
    fun loadRecommendationPickle() {
        Assertions.assertTrue(
            getPickleResponseListFromPickleList().containsAll(
                loadRecommendationPicklesUseCase.loadRecommendationPickles(0).pickleList
            )
        )

    }

    @Test
    fun loadRecommendationPickleWithTooBigIndex() {
        Assertions.assertThrows(ThereNoPicklesException::class.java) {
            loadRecommendationPicklesUseCase.loadRecommendationPickles(Int.MAX_VALUE)
        }
    }

    @RepeatedTest(10)
    fun loadPickleFromId() {
        Assertions.assertTrue(
            getPickleResponseListFromPickleList().contains(
                loadPickleFromIdUseCase.loadPickleFromId(pickleList.random().videoId)
            )
        )
    }

    @Test
    fun loadPickleFromNonExistsId() {
        Assertions.assertThrows(PickleNotFoundException::class.java) {
            loadPickleFromIdUseCase.loadPickleFromId("a")
        }
    }

    private fun getPickleResponseListFromPickleList(): List<PickleResponse> =
        pickleList.map {
            pickleMapper.toDomain(it).toResponse(generateM3u8URLPort.generateURL(it.videoId))
        }
}
