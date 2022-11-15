package com.togedocs.backend.domain.repository;

import com.togedocs.backend.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByCode(String code);
}
