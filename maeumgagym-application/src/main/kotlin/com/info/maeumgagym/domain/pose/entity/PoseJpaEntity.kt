package com.info.maeumgagym.domain.pose.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.base.BaseLongIdEntity
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity

@Entity(name = TableNames.POSE_TABLE)
class PoseJpaEntity(
    simpleName: String,
    exactName: String,
    thumbnail: String,
    poseImages: MutableList<String>,
    easyPart: String,
    exactPart: String,
    startPose: String,
    exerciseWay: String,
    breatheWay: String?,
    caution: String?,
    id: Long? = null
) : BaseLongIdEntity(id) {

    @Column(name = "simple_name", length = 30, nullable = false)
    var simpleName: String = simpleName // 간단한 이름
        protected set

    @Column(name = "exact_name", length = 60, nullable = false)
    var exactName: String = exactName // 정확한 이름
        protected set

    @Column(name = "thumbnail", nullable = false)
    var thumbnail: String = thumbnail // 썸네일
        protected set

    @ElementCollection
    var poseImages: MutableList<String> = poseImages // 자세 이미지
        protected set

    @Column(name = "simple_part", length = 30, nullable = false)
    var simplePart: String = easyPart // 간단한 자극 부위 이름
        protected set

    @Column(name = "exact_part", length = 60, nullable = false)
    var exactPart: String = exactPart // 정확한 자극 부위 이름
        protected set

    @Column(name = "start_pose", length = 100, nullable = false)
    var startPose: String = startPose // 시작 자세
        protected set

    @Column(name = "exercise_way", length = 1500, nullable = false)
    var exerciseWay: String = exerciseWay // 운동 방법
        protected set

    @Column(name = "breathe_way", length = 30)
    var breatheWay: String? = breatheWay // 호흡법
        protected set

    @Column(name = "caution", nullable = false)
    var caution: String? = caution // 주의사항
        protected set
}
