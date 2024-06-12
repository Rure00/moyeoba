package com.moyeoba.moyeoba.data.entity

import com.fasterxml.jackson.annotation.JsonProperty
import com.moyeoba.moyeoba.security.UserRoleEnum
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.yaml.snakeyaml.serializer.Serializer
import java.io.Serializable
import java.util.Collections


@Entity @Table(name = "users")
class User(): Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    var token: String? = null
    var email: String? = null

    var naverId: String? = null
    var kakaoId: Long? = null

    var role: UserRoleEnum = UserRoleEnum.Member

    constructor(kakaoId: Long): this() {
        this.kakaoId = kakaoId
    }
    constructor(naverId: String): this() {
        this.naverId = naverId
    }
}