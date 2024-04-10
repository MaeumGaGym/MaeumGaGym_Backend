package com.info.maeumgagym.domain.user

import com.info.common.responsibility.PersistenceAdapter
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.repository.UserNativeRepository
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.*
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@PersistenceAdapter
internal class UserPersistenceAdapter(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val userNativeRepository: UserNativeRepository
) : SaveUserPort,
    DeleteUserPort,
    ExistUserPort,
    ReadUserPort {

    override fun readDeletedByOauthId(oauthId: String): User? =
        userNativeRepository.findDeletedUserByOauthId(oauthId)?.let { userMapper.toDomain(it) }

    override fun readById(userId: UUID): User? =
        userRepository.findById(userId)?.let { userMapper.toDomain(it) }

    override fun readByNickname(nickname: String): User? =
        userRepository.findByNickname(nickname)?.let { userMapper.toDomain(it) }

    override fun existsByOAuthId(oauthId: String): Boolean =
        userRepository.findByOauthId(oauthId)?.let { true } ?: false

    @Transactional(propagation = Propagation.MANDATORY)
    override fun save(user: User): User =
        userMapper.toDomain(
            userRepository.save(userMapper.toEntity(user))
        )

    override fun readByOAuthId(oauthId: String): User? =
        userRepository.findByOauthId(oauthId)?.let { userMapper.toDomain(it) }

    @Transactional(propagation = Propagation.MANDATORY)
    override fun deleteById(id: UUID) {
        userRepository.deleteById(id)
    }

    override fun existByNicknameOnWithdrawalSafe(nickName: String): Boolean =
        userNativeRepository.findByNicknameOnWithdrawalSafe(nickName)?.let { true } ?: false

    override fun existByOAuthIdOnWithdrawalSafe(oauthId: String): Boolean =
        userNativeRepository.findByOauthIdOnWithdrawalSafe(oauthId)?.let { true } ?: false
}
