package com.info.maeumgagym.domain.pose.entity

import java.util.UUID
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "pose")
class PoseJpaEntity(
    name: String,
    exactName: String,
    thumbnail: String,
    poseImages: MutableList<String>,
    exactPart: String,
    easyPart: String,
    startPose: String,
    exerciseWay: String,
    breatheWay: String?,
    caution: String,
    id: UUID? = null
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)", nullable = false)
    var id: UUID? = id
        protected set

    @Column(name = "name", length = 30, nullable = false)
    var name: String = name // 간단한 이름
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

    @Column(name = "exact_part", nullable = false)
    var exactPart: String = exactPart // 정확한 자극 부위 이름
        protected set

    @Column(name = "easy_part", nullable = false)
    var easyPart: String = easyPart // 쉬운 자극 부위 이름
        protected set

    @Column(name = "start_pose", nullable = false)
    var startPose: String = startPose // 시작 자세
        protected set

    @Column(name = "exercise_way", nullable = false)
    var exerciseWay: String = exerciseWay // 운동 방법
        protected set

    @Column(name = "breathe_way")
    var breatheWay: String? = breatheWay // 호흡법
        protected set

    @Column(name = "caution", nullable = false)
    var caution: String = caution // 주의사항
        protected set
}
