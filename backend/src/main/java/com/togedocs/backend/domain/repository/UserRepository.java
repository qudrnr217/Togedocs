package com.togedocs.backend.domain.repository;

import com.togedocs.backend.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByUuid(String uuid);

    Optional<User> findByEmail(String email);

}
