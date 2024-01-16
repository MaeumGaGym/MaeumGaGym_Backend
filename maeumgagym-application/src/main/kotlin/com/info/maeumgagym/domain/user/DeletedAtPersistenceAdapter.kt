package com.info.maeumgagym.domain.user

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.auth.port.out.DeleteDeletedAtPort
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.repository.DeletedAtRepository
import com.info.maeumgagym.user.model.DeletedAt
import com.info.maeumgagym.user.port.out.FindDeletedAtByIdPort
import com.info.maeumgagym.user.port.out.SaveDeletedAtPort
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate
import java.util.*

@PersistenceAdapter
internal class DeletedAtPersistenceAdapter(
    private val userMapper: UserMapper,
    private val deletedAtRepository: DeletedAtRepository
): SaveDeletedAtPort, FindDeletedAtByIdPort, DeleteDeletedAtPort {

    override fun save(domain: DeletedAt): DeletedAt =
        userMapper.toDomain(deletedAtRepository.save(userMapper.toEntity(domain)))

    override fun findDeletedAt(id: UUID): LocalDate? =
        deletedAtRepository.findByIdOrNull(id)?.date

    override fun delete(userId: UUID) {
        deletedAtRepository.deleteById(userId)
    }
}
