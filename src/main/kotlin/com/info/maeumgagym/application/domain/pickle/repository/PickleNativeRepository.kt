package com.info.maeumgagym.application.domain.pickle.repository

import com.info.maeumgagym.application.TableNames
import com.info.maeumgagym.application.domain.pickle.entity.PickleJpaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param

@org.springframework.stereotype.Repository
interface PickleNativeRepository : Repository<PickleJpaEntity, String> {

    @Query(
        value = "SELECT p.* FROM ${TableNames.PICKLE_TABLE} p " +
            "WHERE (p.tags like '%' + :sTag + '%' OR p.tags like '%' + :eTag + '%') " +
            "AND p.like_count > 50",
        nativeQuery = true
    )
    fun findByTagsContaining(
        @Param("sTag") simpleTag: String,
        @Param("eTag") exactTag: String,
        pageable: Pageable
    ): Page<PickleJpaEntity>
}
