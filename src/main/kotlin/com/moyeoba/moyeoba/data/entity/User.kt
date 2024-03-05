package com.moyeoba.moyeoba.data.entity

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.yaml.snakeyaml.serializer.Serializer
import java.io.Serializable
import java.util.Collections


@Entity @Table(name = "users")
class User(
        phoneNumber: String
): Serializable,UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @Column(nullable = false, unique = true)
    var uid: String = phoneNumber

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    var password: String = "0000"
        private set


    var naverId: String? = null
        private set
    var kakaoId: Long? = null
        private set


    override fun getAuthorities(): Collection<GrantedAuthority> {
        return Collections.emptyList()
    }

    override fun getPassword(): String {
        return this.password
    }

    override fun getUsername(): String {
        return this.uid
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}