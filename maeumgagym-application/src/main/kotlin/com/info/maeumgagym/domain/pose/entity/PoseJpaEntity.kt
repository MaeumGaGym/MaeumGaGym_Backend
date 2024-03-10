package com.info.maeumgagym.domain.pose.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.converter.StringAttributeConverter
import com.info.maeumgagym.domain.base.BaseLongIdEntity
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.ElementCollection
import javax.persistence.Entity

@Entity(name = TableNames.POSE_TABLE)
class PoseJpaEntity(
    simpleName: String,
    exactName: String,
    thumbnail: String,
    poseImages: MutableSet<String>,
    easyPart: MutableSet<String>,
    exactPart: MutableSet<String>,
    startPose: MutableSet<String>,
    exerciseWay: MutableSet<String>,
    breatheWay: MutableSet<String>?,
    caution: MutableSet<String>?,
    id: Long? = null
) : BaseLongIdEntity(id) {

    @Column(name = "simple_name", length = 30, updatable = true, nullable = false)
    var simpleName: String = simpleName // 간단한 이름
        protected set

    @Column(name = "exact_name", length = 60, updatable = true, nullable = false)
    var exactName: String = exactName // 정확한 이름
        protected set

    @Column(name = "thumbnail", updatable = true, nullable = false)
    var thumbnail: String = thumbnail // 썸네일
        protected set

    @ElementCollection
    var poseImages: MutableSet<String> = poseImages // 자세 이미지
        protected set

    @Convert(converter = StringAttributeConverter::class)
    @Column(name = "simple_part", length = 30, updatable = true, nullable = false)
    var simplePart: MutableSet<String> = easyPart // 간단한 자극 부위 이름
        protected set

    @Convert(converter = StringAttributeConverter::class)
    @Column(name = "exact_part", length = 60, updatable = true, nullable = false)
    var exactPart: MutableSet<String> = exactPart // 정확한 자극 부위 이름
        protected set

    @Convert(converter = StringAttributeConverter::class)
    @Column(name = "start_pose", length = 100, updatable = true, nullable = false)
    var startPose: MutableSet<String> = startPose // 시작 자세
        protected set

    @Convert(converter = StringAttributeConverter::class)
    @Column(name = "exercise_way", length = 1500, updatable = true, nullable = false)
    var exerciseWay: MutableSet<String> = exerciseWay // 운동 방법
        protected set

    @Convert(converter = StringAttributeConverter::class)
    @Column(name = "breathe_way", length = 30, updatable = true, nullable = false)
    var breatheWay: MutableSet<String>? = breatheWay // 호흡법
        protected set

    @Convert(converter = StringAttributeConverter::class)
    @Column(name = "caution", updatable = true, nullable = false)
    var caution: MutableSet<String>? = caution // 주의사항
        protected set
}
