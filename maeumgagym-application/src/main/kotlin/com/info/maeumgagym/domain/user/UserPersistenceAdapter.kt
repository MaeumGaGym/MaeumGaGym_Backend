package com.info.maeumgagym.domain.user

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.*
import org.springframework.data.repository.findByIdOrNull
import java.math.BigInteger
import java.util.*

@PersistenceAdapter
class UserPersistenceAdapter(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) : FindUserByUUIDPort,
    SaveUserPort,
    FindUserByOAuthIdPort,
    DeleteUserPort,
    ExistUserByNicknamePort,
    ExistUserByOAuthIdPort,
    FindDeletedUserByIdPort {

    override fun findByIdOrNullInNative(oauthId: String): User? =
        userRepository.findDeletedUserByOauthIdInNative(oauthId)?.let { userMapper.toDomain(it) }


    override fun findUserById(userId: UUID): User? =
        userRepository.findByIdOrNull(userId)?.let { userMapper.toDomain(it) }

    override fun existsUserByOAuthId(oauthId: String): Boolean =
        userRepository.existsByOauthId(oauthId)

    override fun saveUser(user: User): User {
        val userJpaEntity = userRepository.save(userMapper.toEntity(user))
        return userMapper.toDomain(userJpaEntity)
    }

    override fun saveUserAnFlush(user: User): User =
        userMapper.toDomain(
            userRepository.saveAndFlush(userMapper.toEntity(user))
        )

    override fun findUserByOAuthId(oauthId: String): User? =
        userRepository.findByOauthId(oauthId)?.let { userMapper.toDomain(it) }

    override fun deleteUser(user: User) {
        userRepository.delete(userMapper.toEntity(user))
    }

    override fun existByNicknameInNative(nickName: String): Boolean =
        userRepository.existsByNicknameInNative(nickName) > BigInteger.ZERO

    override fun existByOAuthIdInNative(oauthId: String): Boolean =
        userRepository.existsByOauthIdInNative(oauthId) > BigInteger.ZERO
}
