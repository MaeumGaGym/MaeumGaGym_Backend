package com.info.maeumgagym.domain.user

import com.info.common.PersistenceAdapter
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
) : FindUserByUUIDPort,
    SaveUserPort,
    FindUserByOAuthIdPort,
    DeleteUserPort,
    ExistUserByNicknamePort,
    ExistUserByOAuthIdPort,
    FindDeletedUserByIdPort {

    override fun findDeletedUserByOauthId(oauthId: String): User? =
        userNativeRepository.findDeletedUserByOauthId(oauthId)?.let { userMapper.toDomain(it) }

    override fun findUserById(userId: UUID): User? =
        userRepository.findById(userId)?.let { userMapper.toDomain(it) }

    override fun existsUserByOAuthId(oauthId: String): Boolean =
        userRepository.findByOauthId(oauthId)?.let { true } ?: false

    @Transactional(propagation = Propagation.MANDATORY)
    override fun saveUser(user: User): User =
        userMapper.toDomain(
            userRepository.save(userMapper.toEntity(user))
        )

    override fun findUserByOAuthId(oauthId: String): User? =
        userRepository.findByOauthId(oauthId)?.let { userMapper.toDomain(it) }

    @Transactional(propagation = Propagation.MANDATORY)
    override fun deleteById(id: UUID) {
        userRepository.deleteById(id)
    }

    override fun existByNicknameOnWithdrawalSafe(nickName: String): Boolean =
        userNativeRepository.findByNicknameOnWithdrawalSafe(nickName)?.let { true } ?: false

    override fun existUserByOAuthIdOnWithdrawalSafe(oauthId: String): Boolean =
        userNativeRepository.findByOauthIdOnWithdrawalSafe(oauthId)?.let { true } ?: false
}
