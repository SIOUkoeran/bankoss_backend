package com.example.bankoss.security.jwt.filter


import com.example.bankoss.security.jwt.JwtProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import javax.servlet.FilterChain
import javax.servlet.GenericFilter
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class JwtFilter(
    private val jwtProvider: JwtProvider
) : GenericFilter() {
    val logger : Logger = LoggerFactory.getLogger(JwtFilter::class.java)

    var AUTHORIZATION_HEADER : String = "Authorization"

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val req : HttpServletRequest = request as HttpServletRequest
        val jwt : String? = jwtProvider.resolveToken(req)
        val requestUri : String = req.requestURI

        if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)){
            val authentication : Authentication = jwtProvider.getAuthentication(jwt!!)
            SecurityContextHolder.getContext().authentication = authentication
            logger.debug("Security Context save ${authentication.name} uri : $requestUri")
        }else{
            logger.debug("Invalid Jwt --- uri : $requestUri")
        }
        chain.doFilter(request, response)
    }



}