package com.info.maeumgagym.adapter.auth

import com.info.maeumgagym.auth.port.out.DeleteDeletedAtPort
import com.info.maeumgagym.domain.user.repository.DeletedAtRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class DeletedAtAdapter(
    private val deletedAtRepository: DeletedAtRepository
): DeleteDeletedAtPort {

    override fun delete(userId: UUID) {
        deletedAtRepository.deleteById(userId)
    }
}
