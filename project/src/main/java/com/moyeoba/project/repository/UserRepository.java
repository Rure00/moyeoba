package com.moyeoba.project.repository;

import com.moyeoba.project.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getByUid(String uid);
    User getByNaverId(String id);
}
