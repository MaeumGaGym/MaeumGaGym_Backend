package com.info.maeumgagym.domain.user.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import com.info.maeumgagym.domain.user.entity.QUserJpaEntity.userJpaEntity


@Repository
class CustomUserRepositoryImpl(
    private val query: JPAQueryFactory
): CustomUserRepository {
}
