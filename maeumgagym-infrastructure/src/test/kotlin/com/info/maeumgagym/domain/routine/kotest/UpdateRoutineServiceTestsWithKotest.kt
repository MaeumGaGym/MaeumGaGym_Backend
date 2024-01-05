// FIXME("현재 로직에 맞지 않으며, kotest로 작성되어 수정이 어려움.
//  또한, JUnit5 기반의 새로운 테스트 코드가 작성되어 필요하지 않게됨.
//  가급적 삭제를 권하며, 복구를 원할 경우 로직에 맞게 테스트 코드 수정 요함.")
/*
package com.info.maeumgagym.domain.routine.kotest

import com.info.maeumgagym.auth.exception.PermissionDeniedException
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.routine.dto.request.UpdateRoutineRequest
import com.info.maeumgagym.routine.exception.RoutineNotFoundException
import com.info.maeumgagym.routine.model.ExerciseInfoModel
import com.info.maeumgagym.routine.model.Routine
import com.info.maeumgagym.routine.model.RoutineStatusModel
import com.info.maeumgagym.routine.port.out.ReadRoutineByIdPort
import com.info.maeumgagym.routine.port.out.SaveRoutinePort
import com.info.maeumgagym.routine.service.UpdateRoutineService
import com.info.maeumgagym.user.model.Role
import com.info.maeumgagym.user.model.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

internal class UpdateRoutineServiceTestsWithKotest : BehaviorSpec({
    val readRoutineByIdPort = mockk<ReadRoutineByIdPort>()
    val readCurrentUserPort = mockk<ReadCurrentUserPort>()
    val saveRoutinePort = mockk<SaveRoutinePort>()
    val updateRoutineService = UpdateRoutineService(readRoutineByIdPort, readCurrentUserPort, saveRoutinePort)

    Given("루틴을 수정하려는 상황에서") {
        val user1Id = UUID.fromString("e25b8200-9e45-11ee-9c7e-0af86a3dcfb4")
        val routineId = 1L
        val routine = Routine(
            routineName = "아침운동",
            exerciseInfoModelList = mutableListOf(ExerciseInfoModel(exerciseName = "스쿼드", repetitions = 10, sets = 10)),
            dayOfWeeks = null,
            routineStatusModel = RoutineStatusModel(isShared = true, isArchived = true),
            id = routineId,
            userId = user1Id
        )

        val updatedRoutine = Routine(
            id = routineId,
            routineName = "저녁운동",
            exerciseInfoModelList = mutableListOf(ExerciseInfoModel(exerciseName = "헥스쿼트", repetitions = 12, sets = 5)),
            dayOfWeeks = null,
            routineStatusModel = RoutineStatusModel(isArchived = true, isShared = true),
            userId = user1Id
        )

        val updateRoutineReq = UpdateRoutineRequest(
            routineName = "저녁운동",
            exerciseInfoModelList = mutableListOf(ExerciseInfoModel(exerciseName = "헥스쿼트", repetitions = 12, sets = 5)),
            dayOfWeeks = null,
            isShared = true,
            isArchived = true,
        )

        val user1 = User(
            nickname = "현수",
            oauthId = "1234",
            roles = mutableListOf(Role.USER),
            profileImage = null,
            id = user1Id
        )

        When("자신의 루틴을 수정할 때") {
            every { readRoutineByIdPort.readRoutineById(routineId) } returns routine
            every { readCurrentUserPort.readCurrentUser() } returns user1
            every { saveRoutinePort.saveRoutine(any()) } returns updatedRoutine

            val result = updateRoutineService.updateRoutine(updateRoutineReq, routineId)

            Then("루틴이 수정되어야 함") {
                result shouldBe updatedRoutine
                verify(exactly = 1) {
                    saveRoutinePort.saveRoutine(updatedRoutine)
                }
            }
        }

        When("다른 사용자의 루틴을 수정하려고 할 때") {
            val user2Id = UUID.fromString("a25b8200-9e45-11ee-9c7e-0af86a3dcfb4")
            val user2 = User(
                nickname = "현수2",
                oauthId = "12345",
                roles = mutableListOf(Role.USER),
                profileImage = null,
                id = user2Id
            )
            every { readRoutineByIdPort.readRoutineById(routineId) } returns routine
            every { readCurrentUserPort.readCurrentUser() } returns user2

            Then("권한이 없으므로 PermissionDeniedException 예외를 던져야 함") {
                val exception = shouldThrow<PermissionDeniedException> {
                    updateRoutineService.updateRoutine(updateRoutineReq, routineId)
                }
                exception.message shouldBe null
            }
        }

        When("존재하지 않는 루틴을 수정하려고 할 때") {
            every { readRoutineByIdPort.readRoutineById(routineId) } returns null
            every { readCurrentUserPort.readCurrentUser() } returns user1

            Then("루틴을 찾을 수 없으므로 RoutineNotFoundException 예외를 던져야 함") {
                val exception = shouldThrow<RoutineNotFoundException> {
                    updateRoutineService.updateRoutine(updateRoutineReq, routineId)
                }
                exception.message shouldBe null
            }
        }
    }
})
*/
