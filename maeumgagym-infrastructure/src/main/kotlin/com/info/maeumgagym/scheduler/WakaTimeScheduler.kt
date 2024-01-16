package com.info.maeumgagym.scheduler

import com.info.maeumgagym.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Transactional
@Service
class WakaTimeScheduler(
    private val userRepository: UserRepository
) {
}
