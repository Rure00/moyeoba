package com.moyeoba.moyeoba.test.repo

import com.moyeoba.moyeoba.test.Team
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TeamRepository: JpaRepository<Team, Long> {
}