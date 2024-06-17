package com.moyeoba.moyeoba.test

import jakarta.persistence.*

@Entity
@Table()
data class Owner(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {
    @OneToMany(mappedBy = "owner", cascade = [CascadeType.REMOVE], orphanRemoval = true, fetch = FetchType.EAGER)
    val employee = mutableSetOf<Employee>()
}
