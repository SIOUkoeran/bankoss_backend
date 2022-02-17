package com.example.bankoss.exception.security

import com.example.bankoss.exception.CustomException
import com.example.bankoss.exception.ErrorCode

class AccessDeniedException() : CustomException(ErrorCode.ACCESS_DENIED){

    fun getErrorCode() : ErrorCode{
        return ErrorCode.ACCESS_DENIED
    }
}