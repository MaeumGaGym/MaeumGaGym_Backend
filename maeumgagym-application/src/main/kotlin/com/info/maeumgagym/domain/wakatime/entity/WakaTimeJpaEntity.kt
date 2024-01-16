package com.info.maeumgagym.domain.wakatime.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import org.springframework.data.domain.Persistable
import java.io.Serializable
import java.time.LocalDate
import java.util.UUID
import javax.persistence.*

@Entity(name = TableNames.WAKA_TIME_TABLE)
@IdClass(WakaTimeJpaEntity.IdClass::class)
class WakaTimeJpaEntity(
    user: UserJpaEntity,
    waka: Long,
    date: LocalDate
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

    override fun isNew(): Boolean = false // select 두번 후에 insert 쿼리가 나가므로 비효율적, 개선 필요
}
