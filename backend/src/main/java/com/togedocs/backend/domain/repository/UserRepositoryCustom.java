package com.togedocs.backend.domain.repository;

import com.togedocs.backend.api.dto.UserRequest;
import com.togedocs.backend.domain.entity.User;

import java.util.List;

public interface UserRepositoryCustom {
    boolean updateUserInfo(User userEntity, UserRequest.ModifyUserRequest userRequest);

    List<Long> getProjectId(Long id);

    int getImgNo(Long projectId);
}
