package com.moyeoba.moyeoba.test.repo

import com.moyeoba.moyeoba.test.Owner
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OwnerRepository: JpaRepository<Owner, Long> {
}