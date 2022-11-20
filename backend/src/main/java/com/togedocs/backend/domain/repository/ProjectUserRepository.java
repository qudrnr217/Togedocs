package com.togedocs.backend.domain.repository;

import com.togedocs.backend.domain.entity.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, Long>, ProjectUserRepositoryCustom {
    Optional<ProjectUser> findByProjectIdAndUserId(Long projectId, Long userId);

    void deleteByProjectId(Long providerId);
}
