package com.togedocs.backend.domain.repository;

import com.togedocs.backend.api.dto.UserRequest;
import com.togedocs.backend.domain.entity.User;

import java.util.List;

public interface UserRepositoryCustom {
    long updateByUserInfo(User userEntity, UserRequest.ModifyUserRequest userRequest);



    List<Long> getProjectId(Long id);

    List<String> getNames(Long projectId, Long id);

    int getImgNo(Long projectId);
}
