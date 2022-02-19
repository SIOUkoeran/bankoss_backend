package com.example.bankoss.security.service

import com.example.bankoss.domain.user.Repository.UserRepository
import com.example.bankoss.domain.user.entity.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    val logger : Logger = LoggerFactory.getLogger(CustomUserDetailsService::class.java)

    override fun loadUserByUsername(username: String?): UserDetails {
        val user : User = this.userRepository.findOneWithAuthoritiesByUserName(username!!)
            ?: throw UsernameNotFoundException("user : $username not found")
        return createUser(username, user)
    }

    private fun createUser(username : String, user : User) : org.springframework.security.core.userdetails.User{
        val authorities : MutableList<GrantedAuthority> = user.authorities.stream()
            .map { authority -> SimpleGrantedAuthority(authority.authorityName) }
            .collect(Collectors.toList())
        return org.springframework.security.core.userdetails.User(user.userName, user.password, authorities)
    }
}