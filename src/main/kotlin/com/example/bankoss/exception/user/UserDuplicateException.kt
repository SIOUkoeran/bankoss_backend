package com.example.bankoss.exception.user

import com.example.bankoss.exception.CustomException
import com.example.bankoss.exception.ErrorCode

class UserDuplicateException : CustomException(ErrorCode.DUPLICATE_USER) {
    fun getErrorCode() : ErrorCode{
        return ErrorCode.DUPLICATE_USER
    }
}