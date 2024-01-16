package com.info.maeumgagym.routine.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.exception.PermissionDeniedException
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.routine.exception.RoutineNotFoundException
import com.info.maeumgagym.routine.port.`in`.DeleteRoutineUseCase
import com.info.maeumgagym.routine.port.out.DeleteRoutinePort
import com.info.maeumgagym.routine.port.out.ReadRoutineByIdPort

@UseCase
internal class DeleteRoutineService(
    private val deleteRoutinePort: DeleteRoutinePort,
    private val readRoutineByIdPort: ReadRoutineByIdPort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : DeleteRoutineUseCase {
    override fun deleteRoutine(id: Long) {
        // (routine.id = id)인 루틴 찾기, 없다면 -> 예외 처리
        val routine = readRoutineByIdPort.readRoutineById(id) ?: throw RoutineNotFoundException

        // 토큰으로 유저 찾기
        val user = readCurrentUserPort.readCurrentUser()

        // 루틴을 만든 사람이 토큰의 유저가 맞는지 검증, 아니면 -> 예외처리
        if (user.id != routine.userId) throw PermissionDeniedException

        // 루틴 삭제
        deleteRoutinePort.deleteRoutine(routine)
    }
}
