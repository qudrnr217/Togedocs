package com.togedocs.backend.domain.repository;

import com.togedocs.backend.api.dto.UserRequest;
import com.togedocs.backend.domain.entity.User;

import java.util.List;

public interface UserRepositoryCustom {
    long updateByUserInfo(User userEntity, UserRequest.ModifyUserRequest userRequest);


    List<User> getUserInfo(Long id);

    List<Long> getProjectInfo(Long id);
}
