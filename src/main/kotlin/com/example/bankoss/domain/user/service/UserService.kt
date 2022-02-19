package com.example.bankoss.domain.user.service

import com.example.bankoss.domain.user.entity.Authority
import com.example.bankoss.domain.user.Repository.UserRepository
import com.example.bankoss.domain.user.dto.UserLoginResponse
import com.example.bankoss.domain.user.entity.User
import com.example.bankoss.domain.user.dto.UserSignUp
import com.example.bankoss.exception.user.UserDuplicateException
import com.example.bankoss.exception.user.UserEmailDuplicateException
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository,
private val passwordEncoder: PasswordEncoder) {

    val logger = LoggerFactory.getLogger(UserService::class.java)
    @Transactional
    fun signUpUser(userSignUp: UserSignUp) : UserLoginResponse {
        if (this.userRepository.findOneWithAuthoritiesByUserName(userSignUp.username) != null){
            throw UserDuplicateException()
        }
        if (this.userRepository.existsByEmail(userSignUp.email))
            throw UserEmailDuplicateException()

        val authority : Authority = Authority("ROLE_USER")
        logger.info("authority : $authority")
        val user : User = User(userSignUp.username, userSignUp.email, passwordEncoder.encode(userSignUp.password), setOf(authority))
        logger.info("user : $user")
        val savedUser : User = this.userRepository.save(user)
        return UserLoginResponse(savedUser.userName, savedUser.userId!!, savedUser.email)
    }
}