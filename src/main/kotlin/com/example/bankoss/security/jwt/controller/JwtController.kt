package com.example.bankoss.security.jwt.controller


import com.example.bankoss.domain.user.dto.TokenDto
import com.example.bankoss.response.Response
import com.example.bankoss.security.jwt.JwtProvider
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import com.example.bankoss.exception.security.AccessDeniedException
import com.example.bankoss.exception.security.ExpiredTokenException
import com.example.bankoss.exception.security.NotFoundRefreshTokenException
import io.jsonwebtoken.ExpiredJwtException

@RestController
class JwtController(private val jwtProvider: JwtProvider,
                    private val authenticationManagerBuilder: AuthenticationManagerBuilder) {

    val logger = LoggerFactory.getLogger(JwtController::class.java)

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/token/refresh")
    fun refreshToken(request : HttpServletRequest, response : HttpServletResponse) : Response{
        val authorizationHeader = request.getHeader(AUTHORIZATION) ?: throw AccessDeniedException()
        if (authorizationHeader.startsWith("Bearer ")){
            try{
                val refreshToken : String = authorizationHeader.substring("Bearer ".length)
                val authentication : Authentication = jwtProvider.getAuthentication(refreshToken)
                val accessToken : String = jwtProvider.createToken(authentication)
                response.setHeader("Authoriation", accessToken)
                response.setHeader("refresh_token", refreshToken)
                return Response("2000", "토큰이 재발행되었습니다.", TokenDto(accessToken, refreshToken))
            }
            catch (e : ExpiredJwtException){
                throw ExpiredTokenException()
            }catch (e : Exception){
                throw AccessDeniedException()
            }
        }
        throw NotFoundRefreshTokenException()
    }
}