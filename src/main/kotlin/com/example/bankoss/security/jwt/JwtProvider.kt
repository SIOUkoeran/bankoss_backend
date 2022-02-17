package com.example.bankoss.security.jwt

import com.example.bankoss.exception.security.ExpiredTokenException
import com.example.bankoss.exception.security.InvalidSignatureException
import com.example.bankoss.exception.security.InvalidTokenException
import io.jsonwebtoken.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.util.*
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest

@Component
class JwtProvider{
    val logger = LoggerFactory.getLogger(JwtProvider::class.java)

    @Value("\${auth.jwt.secretkey}")
    lateinit var secret : String

    @Value("\${auth.jwt.expireMills}")
    lateinit var tokenValidityMills : String

    @Value("\${auth.jwt.refreshExpireMills}")
    lateinit var refreshTokenValidityMills : String

    val AUTHORIZATION_HEADER : String = "Authorization"


    fun createToken(authentication: Authentication) : String{
        val authorities : String = authentication.authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","))

        val now  = Date().time
        val validity = Date(now + tokenValidityMills.toLong())

        return Jwts.builder()
            .setSubject(authentication.name)
            .claim("auth", authorities)
            .signWith(SignatureAlgorithm.HS512, secret)
            .setExpiration(validity)
            .compact()
    }
    fun createRefreshToken(authentication: Authentication) : String{
        val authorities : String = authentication.authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","))

        val now  = Date().time
        val validity = Date(now + refreshTokenValidityMills.toLong())

        return Jwts.builder()
            .setSubject(authentication.name)
            .claim("auth", authorities)
            .signWith(SignatureAlgorithm.HS512, secret)
            .setExpiration(validity)
            .compact()
    }
    fun getAuthentication(token : String) : Authentication{
        val claims : Claims = Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .body

        val authorities = claims["auth"].toString()
            .split(",").filterNot { it.isEmpty() }
            .map(::SimpleGrantedAuthority)

        val principal : User = User(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    fun validateToken(token : String?) : Boolean{
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
            return true;
        }catch (e : SignatureException){
            logger.error(("잘못된 JWT 서명입니다. ${e.message}"))
            throw InvalidSignatureException()
        }catch (e : ExpiredJwtException){
            logger.error("만료된 JWT : ${e.message}")
            throw ExpiredTokenException()
        } catch (e: UnsupportedJwtException) {
            logger.error("지원하지 않는 토큰 유형입니다. ${e.message}")
            throw com.example.bankoss.exception.security.UnsupportedJwtException()
        } catch (e: IllegalArgumentException) {
            logger.error("JWT 토큰이 잘못되었습니다. ${e.message}")
            throw InvalidTokenException()
        } catch (e : SecurityException){
            logger.error("잘못된 JWT 서명 ${e.message}")
            throw InvalidSignatureException()
        }
    }

    fun resolveToken(request : HttpServletRequest) : String? {
        val bearerToken : String? = request.getHeader(AUTHORIZATION_HEADER)
        if (StringUtils.hasText(bearerToken) && bearerToken!!.startsWith("Bearer ")){
            return bearerToken!!.substring(7)
        }
        return null
    }
    fun getUserIdFromJwt(token : String) : String{
        val claims : Claims = Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .body
        return claims.subject
    }
}