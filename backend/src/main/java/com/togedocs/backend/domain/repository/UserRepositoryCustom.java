package com.togedocs.backend.domain.repository;

import com.togedocs.backend.api.dto.UserRequest;
import com.togedocs.backend.domain.entity.User;

public interface UserRepositoryCustom {
    long updateByUserInfo(User userEntity, UserRequest.ModifyUserRequest userRequest);
}
