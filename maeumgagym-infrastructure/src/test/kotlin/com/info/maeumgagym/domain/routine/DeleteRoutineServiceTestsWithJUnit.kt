package com.info.maeumgagym.domain.routine

import com.info.maeumgagym.domain.routine.entity.ExerciseInfo
import com.info.maeumgagym.domain.routine.entity.RoutineJpaEntity
import com.info.maeumgagym.domain.routine.entity.RoutineStatus
import com.info.maeumgagym.domain.routine.repository.RoutineRepository
import com.info.maeumgagym.routine.service.DeleteRoutineService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import java.util.*

@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class DeleteRoutineServiceTestsWithJUnit @Autowired constructor(
    private val deleteRoutineService: DeleteRoutineService,
    private val routineRepository: RoutineRepository
) {

    @BeforeEach
    fun initialize() {
        routineRepository.save(
            RoutineJpaEntity(
                routineName = "아침운동",
                exerciseInfoList = mutableListOf(ExerciseInfo(exerciseName = "스쿼드", repetitions = 10, sets = 10)),
                dayOfWeeks = null,
                routineStatus = RoutineStatus(isShared = true, isArchived = true),
                userId = UUID.fromString("e25b8200-9e45-11ee-9c7e-0af86a3dcfb4")
            )
        )
    }

    @Test
    fun deleteOtherRoutine() {
        Assertions.assertDoesNotThrow {
            deleteRoutineService.deleteRoutine(
                routineRepository.findAllByUserId(
                    UUID.fromString("e25b8200-9e45-11ee-9c7e-0af86a3dcfb4")
                )[0].id!!
            )
        }
    }
}
