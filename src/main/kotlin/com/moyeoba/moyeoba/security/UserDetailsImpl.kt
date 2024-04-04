package com.moyeoba.moyeoba.security

import com.moyeoba.moyeoba.data.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(val user: User): UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val role = user.role.toString()
        val simpleGrantedAuthority = SimpleGrantedAuthority(role)

        return mutableListOf(simpleGrantedAuthority)
    }

    override fun getPassword(): String = "0000"

    override fun getUsername(): String = user.email!!

    override fun isAccountNonExpired(): Boolean = false

    override fun isAccountNonLocked(): Boolean = false

    override fun isCredentialsNonExpired(): Boolean = false

    override fun isEnabled(): Boolean = false
}