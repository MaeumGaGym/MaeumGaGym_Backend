package com.info.maeumgagym.routine.service

import com.info.common.responsibility.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.routine.port.`in`.DeleteRoutineUseCase
import com.info.maeumgagym.routine.port.out.DeleteRoutinePort
import com.info.maeumgagym.routine.port.out.ReadRoutinePort

@UseCase
internal class DeleteRoutineService(
    private val deleteRoutinePort: DeleteRoutinePort,
    private val readRoutinePort: ReadRoutinePort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : DeleteRoutineUseCase {
    override fun deleteRoutine(id: Long) {
        // (routine.id = id)인 루틴 찾기, 없다면 -> 예외 처리
        val routine = readRoutinePort.readById(id) ?: throw BusinessLogicException.ROUTINE_NOT_FOUND

        // 토큰으로 유저 찾기
        val user = readCurrentUserPort.readCurrentUser()

        // 루틴을 만든 사람이 토큰의 유저가 맞는지 검증, 아니면 -> 예외처리
        if (user.id != routine.userId) throw SecurityException.PERMISSION_DENIED

        // 루틴 삭제
        deleteRoutinePort.delete(routine)
    }
}
