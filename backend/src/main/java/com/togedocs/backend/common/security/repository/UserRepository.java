package com.togedocs.backend.common.security.repository;

import com.togedocs.backend.api.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    //findBy 규칙 -> Username 문법
    //Select * from user where username = 1? 이게 호출이 된다.
    public User findByUsername(String username); //Jpa Query methods라고 검색해보면 알 수 있다.
    public User findByEmail(String email);

    //select * from user where email=?
//    public User findByEmail();
}
