package com.info.maeumgagym.application.domain.pickle

import com.info.maeumgagym.common.annotation.responsibility.PersistenceAdapter
import com.info.maeumgagym.application.domain.pickle.mapper.PickleLikeMapper
import com.info.maeumgagym.application.domain.pickle.repository.PickleLikeRepository
import com.info.maeumgagym.application.domain.pickle.repository.PickleRepository
import com.info.maeumgagym.application.domain.user.mapper.UserMapper
import com.info.maeumgagym.core.pickle.model.PickleLike
import com.info.maeumgagym.core.pickle.port.out.DeletePickleLikePort
import com.info.maeumgagym.core.pickle.port.out.ReadPickleLikePort
import com.info.maeumgagym.core.pickle.port.out.SavePickleLikePort
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@PersistenceAdapter
internal class PickleLikePersistenceAdapter(
    private val pickleLikeRepository: PickleLikeRepository,
    private val pickleRepository: PickleRepository,
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

    override fun readByPickleAndUser(
        pickle: com.info.maeumgagym.core.pickle.model.Pickle,
        user: com.info.maeumgagym.core.user.model.User
    ): PickleLike? =
        pickleLikeRepository.findByPickleAndUser(
            pickleRepository.findById(pickle.videoId)!!,
            userMapper.toEntity(user)
        )?.let { pickleLikeMapper.toDomain(it) }

    @Transactional(propagation = Propagation.MANDATORY)
    override fun delete(pickleLike: PickleLike) {
        pickleLikeRepository.delete(
            pickleLikeMapper.toEntity(pickleLike)
        )
    }
}
