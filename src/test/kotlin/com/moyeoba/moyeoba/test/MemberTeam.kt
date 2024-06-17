package com.moyeoba.moyeoba.test

import jakarta.persistence.*

@Entity
@Table()
class MemberTeam(
    @ManyToOne(cascade = [])
    @JoinColumn(name = "member_id")
    val member: Member,

    @ManyToOne()
    @JoinColumn(name = "team_id")
    val team: Team,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {

}