package com.example.bankoss.security.jwt.config


import com.example.bankoss.security.jwt.JwtProvider
import com.example.bankoss.security.jwt.filter.ExceptionFilter
import com.example.bankoss.security.jwt.filter.JwtFilter
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter



class JwtSecurityConfig(
    private val jwtProvider: JwtProvider
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(http: HttpSecurity) {
        val customFilter : JwtFilter = JwtFilter(jwtProvider)
        val exceptionFilter : ExceptionFilter = ExceptionFilter()
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter::class.java)
        http.addFilterBefore(exceptionFilter, customFilter::class.java)
    }
}