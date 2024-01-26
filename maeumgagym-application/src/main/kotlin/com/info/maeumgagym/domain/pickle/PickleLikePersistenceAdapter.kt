package com.info.maeumgagym.domain.pickle

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.pickle.entity.PickleLikeJpaEntity
import com.info.maeumgagym.domain.pickle.mapper.PickleLikeMapper
import com.info.maeumgagym.domain.pickle.repository.PickleLikeRepository
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.pickle.model.PickleLike
import com.info.maeumgagym.pickle.port.out.DeletePickleLikePort
import com.info.maeumgagym.pickle.port.out.ReadPickleLikePort
import com.info.maeumgagym.pickle.port.out.SavePickleLikePort
import com.info.maeumgagym.user.model.User
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@PersistenceAdapter
internal class PickleLikePersistenceAdapter(
    private val pickleLikeRepository: PickleLikeRepository,
    private val pickleLikeMapper: PickleLikeMapper,
    private val userMapper: UserMapper
) : SavePickleLikePort,
    ReadPickleLikePort,
    DeletePickleLikePort {

    @Transactional(propagation = Propagation.MANDATORY)
    override fun save(pickleLike: PickleLike): PickleLike =
        pickleLikeMapper.toDomain(
            pickleLikeRepository.save(pickleLikeMapper.toEntity(pickleLike))
        )

    override fun readByVideoIdAndUser(videoId: String, user: User): PickleLike? =
        pickleLikeRepository.findById(
            PickleLikeJpaEntity.IdClass(
                videoId, userMapper.toEntity(user)
            )
        )?.let { pickleLikeMapper.toDomain(it) }

    @Transactional(propagation = Propagation.MANDATORY)
    override fun delete(pickleLike: PickleLike) {
        pickleLikeRepository.delete(
            pickleLikeMapper.toEntity(pickleLike)
        )
    }
}
