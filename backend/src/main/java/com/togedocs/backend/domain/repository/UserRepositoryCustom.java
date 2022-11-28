package com.togedocs.backend.domain.repository;

import com.togedocs.backend.api.dto.UserRequest;
import com.togedocs.backend.api.dto.UserResponse;
import com.togedocs.backend.domain.entity.User;

import java.util.List;

public interface UserRepositoryCustom {
    boolean updateUserInfo(Long userId, UserRequest.ModifyUserRequest userRequest);

    List<UserResponse.ProjectInfo> getProjectList(Long userId);
}
