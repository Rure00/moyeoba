package com.moyeoba.moyeoba.test.repo

import com.moyeoba.moyeoba.test.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository: JpaRepository<Member, Long> {
}