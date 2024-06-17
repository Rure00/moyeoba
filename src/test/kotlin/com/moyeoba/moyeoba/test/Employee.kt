package com.moyeoba.moyeoba.test

import jakarta.persistence.*

@Entity @Table()
data class Employee(
    @ManyToOne()
    var owner: Owner? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
)
