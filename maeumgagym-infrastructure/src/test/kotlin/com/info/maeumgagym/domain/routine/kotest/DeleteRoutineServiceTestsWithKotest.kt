package com.info.maeumgagym.domain.routine.kotest

import com.info.maeumgagym.auth.exception.PermissionDeniedException
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.routine.model.ExerciseInfoModel
import com.info.maeumgagym.routine.model.Routine
import com.info.maeumgagym.routine.model.RoutineStatusModel
import com.info.maeumgagym.routine.port.out.DeleteRoutinePort
import com.info.maeumgagym.routine.port.out.ReadRoutineByIdPort
import com.info.maeumgagym.routine.service.DeleteRoutineService
import com.info.maeumgagym.user.model.Role
import com.info.maeumgagym.user.model.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.*
import java.util.*

internal class DeleteRoutineServiceTestsWithKotest : BehaviorSpec({
    val deleteRoutinePort = mockk<DeleteRoutinePort>()
    val readRoutineByIdPort = mockk<ReadRoutineByIdPort>()
    val readCurrentUserPort = mockk<ReadCurrentUserPort>()
    val deleteRoutineService = DeleteRoutineService(deleteRoutinePort, readRoutineByIdPort, readCurrentUserPort)

    Given("루틴을 삭제하려는 상황에서") {
        When("자신의 루틴이 아닌것을 삭제하려고 할 때") {
            Then("예외가 발생하여야 함") {
                every { readRoutineByIdPort.readRoutineById(routineId) } returns routine
                every { readCurrentUserPort.readCurrentUser() } returns user2
                every { deleteRoutinePort.deleteRoutine(routine) } just runs
                shouldThrow<PermissionDeniedException> {
                    deleteRoutineService.deleteRoutine(routineId)
                }
            }
        }
        When("자신의 루틴이라면") {
            every { readRoutineByIdPort.readRoutineById(routineId) } returns routine
            every { readCurrentUserPort.readCurrentUser() } returns user1
            every { deleteRoutinePort.deleteRoutine(routine) } just runs
            deleteRoutineService.deleteRoutine(routineId)

            Then("루틴이 삭제되어야 함") {
                verify(exactly = 1) { deleteRoutinePort.deleteRoutine(routine) }
            }
        }
    }
}) {
    companion object {
        val user1Id = UUID.fromString("e25b8200-9e45-11ee-9c7e-0af86a3dcfb4")
        val user2Id = UUID.fromString("a25b8200-9e45-11ee-9c7e-0af86a3dcfb4")
        val routineId = 1L
        val routine = Routine(
            routineName = "아침운동",
            exerciseInfoModelList = mutableListOf(ExerciseInfoModel(exerciseName = "스쿼드", repetitions = 10, sets = 10)),
            dayOfWeeks = null,
            routineStatusModel = RoutineStatusModel(isShared = true, isArchived = true),
            id = routineId,
            userId = user1Id
        )

        val user1 = User(
            nickname = "현수",
            oauthId = "1234",
            roles = mutableListOf(Role.USER),
            profileImage = null,
            id = user1Id
        )

        val user2 = User(
            nickname = "현수",
            oauthId = "1234",
            roles = mutableListOf(Role.USER),
            profileImage = null,
            id = user2Id
        )
    }
}
