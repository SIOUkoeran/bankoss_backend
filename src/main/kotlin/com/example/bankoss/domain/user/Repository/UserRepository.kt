package com.example.bankoss.domain.user.Repository

import com.example.bankoss.domain.user.entity.User
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserRepository : JpaRepository<User, Long>{
    @EntityGraph(attributePaths = ["authorities"])
    fun findOneWithAuthoritiesByUserName(username : String) : User?

    fun existsByEmail(email: String) : Boolean
}