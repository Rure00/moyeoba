package com.moyeoba.moyeoba.test

import jakarta.persistence.*

@Entity
@Table()
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {
    @OneToMany(mappedBy = "member", cascade = [], fetch = FetchType.EAGER)
    val teams = mutableSetOf<MemberTeam>()
}