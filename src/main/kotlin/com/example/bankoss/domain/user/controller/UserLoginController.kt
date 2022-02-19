package com.example.bankoss.domain.user.controller

import com.example.bankoss.domain.user.entity.User
import com.example.bankoss.domain.user.dto.TokenDto
import com.example.bankoss.domain.user.dto.UserLogin
import com.example.bankoss.domain.user.dto.UserLoginResponse
import com.example.bankoss.domain.user.dto.UserSignUp
import com.example.bankoss.domain.user.service.UserService
import com.example.bankoss.response.Response
import com.example.bankoss.security.jwt.JwtProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.function.EntityResponse
import java.security.Principal
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse


@RestController
class UserLoginController(
    private val jwtProvider: JwtProvider,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val userService: UserService
) {
    val logger : Logger = LoggerFactory.getLogger(UserLoginController::class.java)

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/user/signup")
    fun signUp(@RequestBody userSignUp: UserSignUp) : ResponseEntity<Response> {
        logger.info("${userSignUp.username}, ${userSignUp.password}" )
        val userLoginResponse : UserLoginResponse = this.userService.signUpUser(userSignUp)
        val response :Response = Response("2010", "회원가입 성공",userLoginResponse)
        return ResponseEntity<Response>(response,HttpStatus.OK)
    }

    @GetMapping("/test")
    fun test(principal: Principal) : String{
        return principal.name
    }

    @PostMapping("/user/login")
    @ResponseStatus(HttpStatus.OK)
    fun authorize(@RequestBody userLogin: UserLogin, response: HttpServletResponse) : Response {
        logger.info("${userLogin.username}, ${userLogin.password}" )
        val token : UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userLogin.username, userLogin.password)

        val authentication : Authentication = authenticationManagerBuilder.`object`.authenticate(token)
        val jwt : String = jwtProvider.createToken(authentication)
        val refreshJwt : String = jwtProvider.createRefreshToken(authentication)
        response.setHeader("Authorization", "Bearer $jwt")
        response.setHeader("refresh_token", "Bearer $refreshJwt")
        return Response("2000", "로그인 성공! 토큰이 발행되었습니다.", TokenDto(jwt, refreshJwt))
    }

    private fun createCookie(token : String) : Cookie{
        val cookie : Cookie = Cookie("Authorization", "Bearer " + token)
        cookie.path = "/"
        cookie.maxAge = 3600
        cookie.secure = true
        return cookie
    }

}