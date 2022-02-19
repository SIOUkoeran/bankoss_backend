package com.example.bankoss.domain.user.entity

import javax.persistence.*

@Entity
class Authority(@Column(name = "authority_name", length= 50)
                @Id
                val authorityName : String) {



    override fun toString(): String {
        return "Authority(authorityName='$authorityName')"
    }
}