package com.example.bankoss.exception.security

import com.example.bankoss.exception.CustomException
import com.example.bankoss.exception.ErrorCode

class InvalidTokenException : CustomException(ErrorCode.EXPIRED_TOKEN) {
    fun getErrorCode() : ErrorCode{
        return ErrorCode.EXPIRED_TOKEN
    }
}