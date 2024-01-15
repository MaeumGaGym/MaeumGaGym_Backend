package com.info.maeumgagym.wakatime.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.user.port.out.SaveUserPort
import com.info.maeumgagym.wakatime.service.port.`in`.AddWakatimeUseCase

@UseCase
class AddWakatimeService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val saveUserPort: SaveUserPort
) : AddWakatimeUseCase {

    override fun addWakatime(waka: Long) {
        // 토큰으로 user불러오기
        val user = readCurrentUserPort.readCurrentUser()

        // 유저의 Wakatime을 불러와 더해주기
        val addedWaka = user.wakatime + waka

        // 더한 Wakatime 저장
        user.updateWakatime(addedWaka)

        saveUserPort.saveUser(user)
    }
}
