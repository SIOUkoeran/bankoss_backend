package com.example.bankoss.exception.security

import com.example.bankoss.exception.CustomException
import com.example.bankoss.exception.ErrorCode

class UnsupportedJwtException() : CustomException(ErrorCode.UNSUPPORTED_TOKEN){
}