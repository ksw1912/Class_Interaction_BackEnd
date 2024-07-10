package com.project.echoproject.repository;

import com.project.echoproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    //username을 받아 DB 테이블에서 회원을 조회하는 메소드 작성
    User findByUsername(String username);
    User findByEmail(String email);

    Boolean existsByEmail(String email);
}
