package com.info.maeumgagym.domain.user.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class CustomUserRepositoryImpl(
    private val query: JPAQueryFactory
) : CustomUserRepository
