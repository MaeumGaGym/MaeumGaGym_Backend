package com.info.maeumgagym.domain.user

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.user.exception.UserNotFoundException
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.CreateUserPort
import com.info.maeumgagym.user.port.out.FindUserByOAuthIdPort
import com.info.maeumgagym.user.port.out.FindUserPort
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@PersistenceAdapter
class UserPersistenceAdapter(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) : FindUserPort, CreateUserPort, FindUserByOAuthIdPort {
    override fun findUserById(userId: UUID): User {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotFoundException
        return userMapper.toDomain(user)
    }

    override fun createUser(user: User): User {
        val userJpaEntity = userRepository.save(userMapper.toEntity(user))
        return userMapper.toDomain(userJpaEntity)
    }

    override fun findUserByOAuthId(oauthId: String): User? =
        userRepository.findByOauthId(oauthId)?.let { userMapper.toDomain(it) }
}
