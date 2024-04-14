package com.info.maeumgagym.domain.pose

import com.info.maeumgagym.domain.pose.entity.PoseJpaEntity
import com.info.maeumgagym.domain.pose.repository.PoseRepository

object PoseTestModule {

    private const val NEED_MACHINE = false
    private const val SIMPLE_NAME = "테스트 포즈"
    private const val EXACT_NAME = "자세한 테스트 포즈"
    private const val THUMBNAIL = "null"
    private const val VIDEO = ""
    private val EASY_PART = mutableSetOf("허벅지")
    private val EXACT_PART = mutableSetOf("대퇴근")
    private val START_POSE = mutableSetOf("시작 자세")
    private val EXERCiSE_WAY = mutableSetOf("운동 방법")
    private val BREATH_WAY = mutableSetOf("호흡법")
    private val CAUTION = mutableSetOf("주의")

    fun createPose(): PoseJpaEntity =
        PoseJpaEntity(
            needMachine = NEED_MACHINE,
            simpleName = SIMPLE_NAME,
            exactName = EXACT_NAME,
            thumbnail = THUMBNAIL,
            video = VIDEO,
            easyPart = EASY_PART,
            exactPart = EXACT_PART,
            startPose = START_POSE,
            exerciseWay = EXERCiSE_WAY,
            breatheWay = BREATH_WAY,
            caution = CAUTION
        )

    fun PoseJpaEntity.saveInRepository(poseRepository: PoseRepository): PoseJpaEntity =
        poseRepository.save(this)
}
