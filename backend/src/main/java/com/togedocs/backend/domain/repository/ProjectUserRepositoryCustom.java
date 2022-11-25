package com.togedocs.backend.domain.repository;

import com.togedocs.backend.api.dto.ProjectRequest;
import com.togedocs.backend.api.dto.UserDto;

import java.util.List;

public interface ProjectUserRepositoryCustom {
    List<UserDto> getMembers(Long projectId);

    Long updateMemberRole(Long projectId, ProjectRequest.UpdateMemberRoleRequest request);

    List<String> getMemberNames(Long projectId);
}
