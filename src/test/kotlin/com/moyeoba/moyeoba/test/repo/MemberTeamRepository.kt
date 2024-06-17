package com.moyeoba.moyeoba.test.repo

import com.moyeoba.moyeoba.test.MemberTeam
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberTeamRepository: JpaRepository<MemberTeam, Long> {
}