package com.pj.project.repository;

import com.pj.project.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);   //소셜로그인 반환값 email로 중복체크
    Optional<User> findByName(String name);
    boolean existsByName(String name); //아이디 중복체크 //true = 중복


}