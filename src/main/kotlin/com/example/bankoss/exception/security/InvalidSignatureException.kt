package com.example.bankoss.exception.security

import com.example.bankoss.exception.CustomException
import com.example.bankoss.exception.ErrorCode

class InvalidSignatureException : CustomException(ErrorCode.INVALID_SIGNATURE) {
    fun getErrorCode() : ErrorCode{
        return ErrorCode.INVALID_SIGNATURE
    }
}