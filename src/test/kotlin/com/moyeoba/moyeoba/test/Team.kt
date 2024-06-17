package com.moyeoba.moyeoba.test

import jakarta.persistence.*

@Entity
@Table()
class Team(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {
    @OneToMany(mappedBy = "team", cascade = [], fetch = FetchType.EAGER)
    val members = mutableSetOf<MemberTeam>()
}