package com.example.bankoss.domain.user.entity

import javax.persistence.*


@Entity
class User(
    @Column(nullable = false, unique = true, name =  "username")
    val userName: String,
    @Column(nullable = false, unique = true)
    val email: String,
    @Column(nullable = true)
    var password: String,

    @ManyToMany
    @JoinTable(
        name = "user_authority",
        joinColumns = (arrayOf(JoinColumn(name = "user_id", referencedColumnName = "user_id"))),
        inverseJoinColumns = (arrayOf(JoinColumn(name = "authority_name", referencedColumnName = "authority_name"))))
    var authorities : Set<Authority>
) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val userId: Long? = null


    override fun toString(): String {
        return "User(userName='$userName', email='$email', password='$password', authorities=$authorities, userId=$userId)"
    }

}