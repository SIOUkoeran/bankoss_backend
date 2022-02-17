package com.example.bankoss.exception.security

import com.example.bankoss.exception.CustomException
import com.example.bankoss.exception.ErrorCode

class NotFoundRefreshTokenException() : CustomException(ErrorCode.NOT_FOUND_REFRESHTOKEN){
    fun getErrorCode() : ErrorCode{
        return ErrorCode.NOT_FOUND_REFRESHTOKEN
    }
}