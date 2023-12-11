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
    breatheWay: String,
    caution: String,
    id: UUID? = null
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)", nullable = false)
    var id: UUID? = id
        protected set

    @Column(name = "name", length = 30, nullable = false)
    var name: String = name
        protected set

    @Column(name = "exact_name", length = 60, nullable = false)
    var exactName: String = exactName
        protected set

    @Column(name = "thumbnail", nullable = false)
    var thumbnail: String = thumbnail
        protected set

    @ElementCollection
    var poseImages: MutableList<String> = poseImages
        protected set

    @Column(name = "exact_part", nullable = false)
    var exactPart: String = exactPart
        protected set

    @Column(name = "easy_part", nullable = false)
    var easyPart: String = easyPart
        protected set

    @Column(name = "start_pose", nullable = false)
    var startPose: String = startPose
        protected set

    @Column(name = "exercise_way", nullable = false)
    var exerciseWay: String = exerciseWay
        protected set

    @Column(name = "breathe_way", nullable = false)
    var breatheWay: String = breatheWay
        protected set

    @Column(name = "caution", nullable = false)
    var caution: String = caution
        protected set
}
