package com.info.maeumgagym.scheduler

import com.info.maeumgagym.domain.user.repository.UserNativeRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = [Exception::class])
class UserScheduler(
    private val userNativeRepository: UserNativeRepository
) {

    // 매일 3시 0분 0초에 작동
    @Scheduled(cron = "0 0 3 * * ?", zone = "Asia/Seoul")
    fun deleteAtOneMonthAgoUserClear() {
        // 탈퇴 후 30일 지난 유저 모두 불러와서 hard deleteById
        userNativeRepository.deleteAllWithdrawalUserOneMonthAgo()
    }
}
