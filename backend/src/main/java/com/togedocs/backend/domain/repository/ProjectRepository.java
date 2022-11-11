package com.togedocs.backend.domain.repository;

import com.togedocs.backend.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
