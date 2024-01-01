package com.info.maeumgagym.domain.user

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.repository.DeletedAtRepository
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.user.model.DeleteAt
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.ExistsDeletedUserByIdPort
import com.info.maeumgagym.user.port.out.FindDeletedAtByIdPort
import com.info.maeumgagym.user.port.out.FindDeletedUserByIdPort
import com.info.maeumgagym.user.port.out.SaveDeletedAtPort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import java.math.BigInteger
import java.time.LocalDate
import java.util.UUID

@Transactional
@PersistenceAdapter
class DeletedAtPersistenceAdapter(
    private val userMapper: UserMapper,
    private val deletedAtRepository: DeletedAtRepository
): SaveDeletedAtPort, FindDeletedAtByIdPort {

    override fun save(domain: DeleteAt): DeleteAt =
        userMapper.toDomain(deletedAtRepository.save(userMapper.toEntity(domain)))

    override fun findDeletedAt(id: UUID): LocalDate? =
        deletedAtRepository.findByIdOrNull(id)?.date
}
