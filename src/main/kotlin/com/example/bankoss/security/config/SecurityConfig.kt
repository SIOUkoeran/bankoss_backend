package com.example.bankoss.security.config

import com.example.bankoss.security.jwt.JwtProvider
import com.example.bankoss.security.jwt.config.JwtSecurityConfig
import com.example.bankoss.security.jwt.failureHandler.JwtAccessDeniedHandler
import com.example.bankoss.security.jwt.failureHandler.JwtAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@EnableWebSecurity
class SecurityConfig(
    private val jwtProvider: JwtProvider,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {

        http
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()
            .headers().frameOptions().sameOrigin()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable()
            .authorizeRequests()
            .antMatchers("/user/login", "/user/signup","/token/refresh").permitAll()
            .anyRequest().authenticated()
            .and()
            .apply(JwtSecurityConfig(jwtProvider))




    }

    @Bean
    fun passwordEncoder()  : PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}