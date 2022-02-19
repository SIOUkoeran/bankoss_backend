package com.example.bankoss.exception.user.handler

import com.example.bankoss.exception.ErrorCode
import com.example.bankoss.exception.ErrorResponse
import com.example.bankoss.exception.user.UserDuplicateException
import com.example.bankoss.exception.user.UserEmailDuplicateException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class UserExceptionHandler {

    val logger = LoggerFactory.getLogger(UserExceptionHandler::class.java)
    @ExceptionHandler(UserDuplicateException::class)
    protected fun handleUserDuplicateException(e : UserDuplicateException) : ResponseEntity<ErrorResponse>{
        logger.error("유저 중복 ${e.message}")
        val errorCode : ErrorCode = e.getErrorCode()
        val response : ErrorResponse = ErrorResponse(errorCode.status, errorCode.message)
        return ResponseEntity<ErrorResponse>(response, HttpStatus.OK)
    }
    @ExceptionHandler(UserEmailDuplicateException::class)
    protected fun handleUserEmailDuplicateException(e : UserEmailDuplicateException) : ResponseEntity<ErrorResponse>{
        logger.error("유저 중복 ${e.message}")
        val errorCode : ErrorCode = e.getErrorCode()
        val response : ErrorResponse = ErrorResponse(errorCode.status, errorCode.message)
        return ResponseEntity<ErrorResponse>(response, HttpStatus.OK)
    }
}