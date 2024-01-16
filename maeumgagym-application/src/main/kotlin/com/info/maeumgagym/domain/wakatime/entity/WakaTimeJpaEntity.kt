package com.info.maeumgagym.domain.wakatime.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import org.springframework.data.domain.Persistable
import java.io.Serializable
import java.time.LocalDate
import java.util.*
import javax.persistence.*


@Entity(name = TableNames.WAKA_TIME_TABLE)
@IdClass(WakaTimeJpaEntity.IdClass::class)
class WakaTimeJpaEntity(
    user: UserJpaEntity,
    waka: Long,
    date: LocalDate,
    isNew: Boolean = true,
): Persistable<WakaTimeJpaEntity.IdClass> {

    @Id @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", columnDefinition = "BINARY(16)")
    var user: UserJpaEntity = user
        protected set

    @Id @Column(name = "date", nullable = false)
    var date: LocalDate = date
        protected set

    @Column(name = "seconds", nullable = false)
    var waka: Long = waka
        protected set

    data class IdClass (
        val user: UUID? = null,
        val date: LocalDate? = null
    ): Serializable

    override fun getId(): IdClass =
        IdClass(
            user = user.id,
            date = date
        )

    @Transient // 엔티티(객체)의 isNew를 테이블(DB)의 컬럼과 매핑 하지 않도록 함
    private var isNew: Boolean = isNew

    @PrePersist // 새로운 Entity에 대해 persist가 호출되기 전 event
    @PostLoad // Entity가 로드된 후 event
    private fun markNotNew() {
        // 만약 DB에서 조회된 객체면 isNew는 false 새로 생성된 객체면 isNew는 true(default)의 값을 가진다
        this.isNew = false
    }

    override fun isNew(): Boolean = this.isNew
}
