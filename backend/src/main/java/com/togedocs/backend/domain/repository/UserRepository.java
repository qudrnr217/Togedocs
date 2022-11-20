package com.togedocs.backend.domain.repository;

import com.togedocs.backend.api.dto.UserResponse;
import com.togedocs.backend.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom{

    //findBy 규칙 -> Username 문법
    //Select * from user where username = 1? 이게 호출이 된다.
    public User findByProviderId(String providerId); //Jpa Query methods라고 검색해보면 알 수 있다.
    public User findByEmail(String email);

    //select * from user where email=?
//    public User findByEmail();
}
