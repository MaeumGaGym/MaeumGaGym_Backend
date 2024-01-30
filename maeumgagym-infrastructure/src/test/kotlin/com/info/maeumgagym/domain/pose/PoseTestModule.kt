package com.info.maeumgagym.domain.pose

import com.info.maeumgagym.domain.pose.entity.PoseJpaEntity
import com.info.maeumgagym.domain.pose.repository.PoseRepository

object PoseTestModule {

    private const val SIMPLE_NAME = "테스트 포즈"
    private const val EXACT_NAME = "자세한 테스트 포즈"
    private const val THUMBNAIL = "null"
    private val IMAGES = listOf("", "")
    private const val EASY_PART = "허벅지"
    private const val EXACT_PART = "대퇴근"
    private const val START_POSE = "시작 자세"
    private const val EXERCiSE_WAY = "운동 방법"
    private const val BREATH_WAY = "호흡법"
    private const val CAUTION = "주의"

    fun createPose(): PoseJpaEntity =
        PoseJpaEntity(
            simpleName = SIMPLE_NAME,
            exactName = EXACT_NAME,
            thumbnail = THUMBNAIL,
            poseImages = IMAGES.toMutableList(),
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
