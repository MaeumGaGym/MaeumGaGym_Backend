package com.info.maeumgagym.domain.user

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.pose.mapper.PoseMapper
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import java.math.BigInteger
import java.util.*

@Transactional
@PersistenceAdapter
class UserPersistenceAdapter(
    private val userRepository: UserRepository,
    private val poseMapper: PoseMapper
) : FindUserByUUIDPort,
    SaveUserPort,
    FindUserByOAuthIdPort,
    DeleteUserPort,
    ExistUserByNicknamePort,
    ExistUserByOAuthIdPort {
    override fun findUserById(userId: UUID): User? =
        userRepository.findByIdOrNull(userId)?.let { poseMapper.toDomain(it) }

    override fun saveUser(user: User): User {
        val userJpaEntity = userRepository.save(poseMapper.toEntity(user))
        return poseMapper.toDomain(userJpaEntity)
    }

    override fun findUserByOAuthId(oauthId: String): User? =
        userRepository.findByOauthId(oauthId)?.let { poseMapper.toDomain(it) }

    override fun deleteUser(user: User) {
        val userJpaEntity = poseMapper.toEntity(user)
        userRepository.delete(userJpaEntity)
    }

    override fun existByNickname(nickName: String): Boolean =
        userRepository.existsByNickname(nickName) > BigInteger.ZERO

    override fun existByOAuthId(oauthId: String): Boolean =
        userRepository.existsByOauthId(oauthId) > BigInteger.ZERO
}
