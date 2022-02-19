package com.example.bankoss.security.jwt.filter

import com.example.bankoss.exception.ErrorCode
import com.example.bankoss.exception.ErrorResponse
import com.example.bankoss.exception.security.ExpiredTokenException
import com.example.bankoss.exception.security.InvalidSignatureException
import com.example.bankoss.exception.security.InvalidTokenException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.SignatureException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import java.time.LocalDateTime
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ExceptionFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try{
            filterChain.doFilter(request, response)
        }catch (e : InvalidSignatureException){
            send(response, ErrorCode.INVALID_SIGNATURE)
        }catch (e : ExpiredTokenException){
            send(response, ErrorCode.EXPIRED_TOKEN)
        } catch (e: com.example.bankoss.exception.security.UnsupportedJwtException) {
            logger.error("지원하지 않는 토큰 유형입니다. ${e.message}")
            send(response, ErrorCode.UNSUPPORTED_TOKEN)
        } catch (e: InvalidTokenException) {
            logger.error("JWT 토큰이 잘못되었습니다. ${e.message}")
            send(response, ErrorCode.INVALID_TOKEN)
        } catch (e : InvalidSignatureException){
            logger.error("잘못된 JWT 서명 ${e.message}")
            send(response, ErrorCode.INVALID_SIGNATURE)
        }
    }
    private fun send(response : HttpServletResponse, errorCode: ErrorCode){
        response.status = HttpStatus.OK.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        val errorResponse : ErrorResponse = ErrorResponse(errorCode.status, errorCode.message)
        val objectMapper : ObjectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        objectMapper.writeValue(response.writer, errorResponse)
    }
}