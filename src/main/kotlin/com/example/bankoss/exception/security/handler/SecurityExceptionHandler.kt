package com.example.bankoss.exception.security.handler

import com.example.bankoss.exception.ErrorCode
import com.example.bankoss.exception.ErrorResponse
import com.example.bankoss.exception.security.*
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.SignatureException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class SecurityExceptionHandler {
    val logger : Logger = LoggerFactory.getLogger(SecurityExceptionHandler::class.java)

    @ExceptionHandler(AccessDeniedException::class)
    protected fun handleAccessDeniedException(e : AccessDeniedException) : ResponseEntity<ErrorResponse>{
        logger.error("AccessDenied! ${e.message}")
        val errorCode : ErrorCode = e.getErrorCode()
        val response : ErrorResponse = ErrorResponse(errorCode.status, errorCode.message)
        return ResponseEntity<ErrorResponse>(response, HttpStatus.OK)
    }

    @ExceptionHandler(NotFoundRefreshTokenException::class)
    protected fun handleNotFoundRefreshTokenException(e : NotFoundRefreshTokenException) : ResponseEntity<ErrorResponse>{
        logger.error("refreshToken is not Found! ${e.message}")
        val errorCode : ErrorCode = e.getErrorCode()
        val response : ErrorResponse = ErrorResponse(errorCode.status, errorCode.message)
        return ResponseEntity<ErrorResponse>(response, HttpStatus.OK)
    }
    @ExceptionHandler(ExpiredTokenException::class)
    protected fun handleNotFoundRefreshTokenException(e : ExpiredTokenException) : ResponseEntity<ErrorResponse>{
        logger.error("refreshToken is expired! ${e.message}")
        val errorCode : ErrorCode = e.getErrorCode()
        val response : ErrorResponse = ErrorResponse(errorCode.status, errorCode.message)
        return ResponseEntity<ErrorResponse>(response, HttpStatus.OK)
    }
    @ExceptionHandler(SignatureException::class)
    protected fun handleInvalidTokenException(e : SignatureException) : ResponseEntity<ErrorResponse>{
        logger.error("token is invalid! ${e.message}")
        val errorCode : ErrorCode = ErrorCode.INVALID_SIGNATURE
        val response : ErrorResponse = ErrorResponse(errorCode.status, errorCode.message)
        return ResponseEntity<ErrorResponse>(response, HttpStatus.OK)
    }

    @ExceptionHandler(AuthenticationException::class)
    protected fun handleLoginFailed(e : AuthenticationException) : ResponseEntity<ErrorResponse>{
        logger.error("로그인 실패 ${ErrorCode.FAIL_USER_LOGIN}")
        val response : ErrorResponse = ErrorResponse(ErrorCode.FAIL_USER_LOGIN.status, ErrorCode.FAIL_USER_LOGIN.message)
        return ResponseEntity<ErrorResponse>(response, HttpStatus.OK)
    }

    @ExceptionHandler(InvalidSignatureException::class)
    protected fun handleInvalidSignatureException(e : InvalidSignatureException) : ResponseEntity<ErrorResponse>{
        logger.error("token signature is invalid! ${e.message}")
        val errorCode : ErrorCode = e.getErrorCode()
        val response : ErrorResponse = ErrorResponse(ErrorCode.INVALID_SIGNATURE.status, ErrorCode.INVALID_SIGNATURE.message)
        return ResponseEntity<ErrorResponse>(response, HttpStatus.OK)
    }




}