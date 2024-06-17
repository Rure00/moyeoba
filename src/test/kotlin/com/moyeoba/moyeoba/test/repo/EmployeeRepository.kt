package com.moyeoba.moyeoba.test.repo

import com.moyeoba.moyeoba.test.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository: JpaRepository<Employee, Long> {
}