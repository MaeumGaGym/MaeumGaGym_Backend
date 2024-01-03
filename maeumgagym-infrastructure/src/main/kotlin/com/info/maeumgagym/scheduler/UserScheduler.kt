package com.info.maeumgagym.scheduler

import com.info.maeumgagym.domain.user.repository.UserRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserScheduler(
    private val userRepository: UserRepository
) {

    // 매일 0시 0분 0초에 작동
    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")
    fun deleteAtOneMonthAgoUserClear() {
        // 탈퇴 후 30일 지난 유저 모두 불러와서 hard delete
        userRepository.deleteAllWithdrawalUserOneMonthAgo()
    }
}
