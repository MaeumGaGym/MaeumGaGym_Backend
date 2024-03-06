package com.info.maeumgagym.domain.pickle

import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.domain.auth.AuthTestModule.saveInContext
import com.info.maeumgagym.domain.pickle.PickleTestModule.createPickle
import com.info.maeumgagym.domain.pickle.PickleTestModule.saveInRepository
import com.info.maeumgagym.domain.pickle.entity.PickleJpaEntity
import com.info.maeumgagym.domain.pickle.repository.PickleLikeRepository
import com.info.maeumgagym.domain.pickle.repository.PickleRepository
import com.info.maeumgagym.domain.user.UserTestModule
import com.info.maeumgagym.domain.user.UserTestModule.saveInRepository
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.error.TestException
import com.info.maeumgagym.pickle.port.`in`.LikePickleUseCase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class LikePickleServiceTests @Autowired constructor(
    private val likePickleUseCase: LikePickleUseCase,
    private val pickleLikeRepository: PickleLikeRepository,
    private val pickleRepository: PickleRepository,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) {

    private lateinit var user: UserJpaEntity
    private lateinit var pickle: PickleJpaEntity

    @BeforeEach
    fun initialize() {
        user = UserTestModule.createTestUser().saveInRepository(userRepository).saveInContext(userMapper)
        pickle = PickleTestModule.createPickle(user).saveInRepository(pickleRepository)
    }

    /**
     * @see LikePickleUseCase.likePickle
     * @when 성공 상황 : 좋아요를 누르지 않은 피클에 좋아요를 누름
     * @fail 피클 엔티티가 정상적으로 조회되는지 확인
     * @fail 피클 좋아요 엔티티가 정상적으로 저장되는지 확인
     * @fail 피클의 좋아요 수가 정상적으로 증가하는지 확인
     * @fail pickle 변수가 영속성 관리를 받는지 확인
     *  @see pickle
     */
    @Test
    fun likePickle() {
        val likeCountBeforeLike = pickle.likeCount

        likePickleUseCase.likePickle(pickle.videoId)

        Assertions.assertNotNull(
            pickleLikeRepository.findByPickleAndUser(pickle, user)
        )
        Assertions.assertTrue(
            likeCountBeforeLike + 1 == pickle.likeCount
        )
    }

    /**
     * @see LikePickleUseCase.likePickle
     * @when 성공 상황 : 좋아요를 누른 피클에 좋아요를 누름(취소)
     * @fail 피클 엔티티가 정상적으로 조회되는지 확인
     * @fail 피클 좋아요 엔티티가 정상적으로 조회되는지 확인
     * @fail 피클 좋아요 엔티티가 정상적으로 삭제되는지 확인
     * @fail 피클의 좋아요 수가 정상적으로 감소하는지 확인
     * @fail pickle 변수가 영속성 관리를 받는지 확인
     *  @see pickle
     */
    @Test
    fun unlikePickle() {
        val likeCountBeforeLike = pickle.likeCount

        likePickleUseCase.likePickle(pickle.videoId)

        val likeCountAfterLike = pickle.likeCount

        likePickleUseCase.likePickle(pickle.videoId)

        Assertions.assertNull(
            pickleLikeRepository.findByPickleAndUser(pickle, user)
        )
        Assertions.assertTrue(
            likeCountBeforeLike == pickle.likeCount &&
                likeCountAfterLike - 1 == pickle.likeCount
        )
    }

    /**
     * @see LikePickleUseCase.likePickle
     * @when 실패 상황 : 존재하지 않는 피클에 좋아요 요청을 보냄
     * @fail 피클 엔티티의 null 여부를 제대로 확인하는지 확인
     * @fail 피클 좋아요 엔티티가 비정상적으로 저장되는지 확인
     * @fail 트랜잭션이 적용되는지 확인
     */
    @Test
    fun likeNonExistsPickle() {
        pickle = createPickle(user)

        TestException.assertThrowsMaeumGaGymExceptionInstance(BusinessLogicException.PICKLE_NOT_FOUND) {
            likePickleUseCase.likePickle(pickle.videoId)
        }
        Assertions.assertNull(
            pickleLikeRepository.findByPickleAndUser(pickle, user)
        )
    }
}
