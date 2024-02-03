package com.info.maeumgagym.domain.routine

import com.info.maeumgagym.domain.routine.entity.ExerciseInfo
import com.info.maeumgagym.domain.routine.entity.RoutineJpaEntity
import com.info.maeumgagym.domain.routine.entity.RoutineStatus
import com.info.maeumgagym.domain.routine.repository.RoutineRepository
import com.info.maeumgagym.routine.dto.request.CreateRoutineRequest
import com.info.maeumgagym.routine.dto.request.UpdateRoutineRequest
import com.info.maeumgagym.routine.model.ExerciseInfoModel
import java.time.DayOfWeek
import java.util.*

internal object RoutineTestModule {

    private const val TEST_ROUTINE_NAME = "테스트 루틴 이름"
    private val TEST_ROUTINE_STATUS = RoutineStatus(isArchived = false, isShared = false)
        get() = field.copy()
    private val TEST_ROUTINE_FIRST_EXERCISE = ExerciseInfo("첫번째 운동", 15, 3)
        get() = field.copy()
    private val TEST_ROUTINE_SECOND_EXERCISE = ExerciseInfo("두번째 운동", 10, 5)
        get() = field.copy()
    private val TEST_ROUTINE_EXERCISE_LIST = listOf(TEST_ROUTINE_FIRST_EXERCISE, TEST_ROUTINE_SECOND_EXERCISE)
        get() = field.toMutableList()
    private val TEST_ROUTINE_DAY_OF_WEEKS = setOf(DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY)
        get() = field.toMutableSet()

    fun createTestRoutine(userId: UUID): RoutineJpaEntity =
        RoutineJpaEntity(
            routineName = TEST_ROUTINE_NAME,
            exerciseInfoList = TEST_ROUTINE_EXERCISE_LIST.toMutableList(),
            dayOfWeeks = TEST_ROUTINE_DAY_OF_WEEKS.toMutableSet(),
            routineStatus = TEST_ROUTINE_STATUS,
            userId = userId
        )

    fun getCreateRoutineRequest(): CreateRoutineRequest =
        CreateRoutineRequest(
            routineName = TEST_ROUTINE_NAME,
            isArchived = TEST_ROUTINE_STATUS.isArchived,
            isShared = TEST_ROUTINE_STATUS.isShared,
            exerciseInfoModelList = TEST_ROUTINE_EXERCISE_LIST.map {
                ExerciseInfoModel(
                    it.exerciseName,
                    it.repetitions,
                    it.sets
                )
            }.toMutableList(),
            dayOfWeeks = TEST_ROUTINE_DAY_OF_WEEKS.toMutableSet()
        )

    fun getUpdateRoutineRequest(originRoutineEntity: RoutineJpaEntity): UpdateRoutineRequest =
        UpdateRoutineRequest(
            routineName = originRoutineEntity.routineName,
            isArchived = originRoutineEntity.routineStatus.isArchived,
            isShared = originRoutineEntity.routineStatus.isShared,
            exerciseInfoModelList = originRoutineEntity.exerciseInfoList.map {
                ExerciseInfoModel(
                    it.exerciseName,
                    it.repetitions,
                    it.sets
                )
            }.toMutableList(),
            dayOfWeeks = mutableSetOf(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY)
        )

    fun RoutineJpaEntity.saveInRepository(routineRepository: RoutineRepository): RoutineJpaEntity =
        routineRepository.save(this)

    fun RoutineJpaEntity.setArchived(boolean: Boolean): RoutineJpaEntity =
        RoutineJpaEntity(
            routineName = routineName,
            exerciseInfoList = exerciseInfoList,
            dayOfWeeks = dayOfWeeks,
            routineStatus = RoutineStatus(boolean, routineStatus.isShared),
            createdAt = createdAt,
            id = id,
            userId = userId
        )

    fun RoutineJpaEntity.setShared(boolean: Boolean): RoutineJpaEntity =
        RoutineJpaEntity(
            routineName = routineName,
            exerciseInfoList = exerciseInfoList,
            dayOfWeeks = dayOfWeeks,
            routineStatus = RoutineStatus(routineStatus.isArchived, boolean),
            createdAt = createdAt,
            id = id,
            userId = userId
        )
}
