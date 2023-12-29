package com.info.maeumgagym.global.scheduler

import com.info.maeumgagym.domain.user.repository.UserRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserScheduler(
    private val userRepository: UserRepository
) {

    @Scheduled(cron = "0 0 * * * ?", zone = "Asia/Seoul")
    fun deleteAtOneMonthAgoUserClear() {
        userRepository.deleteAllWithdrawalUserOneMonthAgo()
    }
}
