package com.togedocs.backend.api.service;

import com.togedocs.backend.api.dto.UserRequest;
import com.togedocs.backend.api.dto.UserResponse;
import com.togedocs.backend.common.exception.BusinessException;
import com.togedocs.backend.common.exception.ErrorCode;
import com.togedocs.backend.domain.entity.Apidocs;
import com.togedocs.backend.domain.entity.ProjectUserRole;
import com.togedocs.backend.domain.entity.User;
import com.togedocs.backend.domain.repository.ApidocsRepository;
import com.togedocs.backend.domain.repository.ProjectUserRepository;
import com.togedocs.backend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ApidocsRepository apidocsRepository;

    public User findUserByUuid(String uuid) {
        return userRepository.findByUuid(uuid)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    public void updateUserInfo(String uuid, UserRequest.ModifyUserRequest userRequest) {
        User user = findUserByUuid(uuid);
        boolean result = userRepository.updateUserInfo(user.getId(), userRequest);
        if (!result) {
            throw new BusinessException(ErrorCode.USER_INFO_UPDATE_BAD_REQUEST);
        }
    }

    public UserResponse.UserInfo getUserInfo(String uuid) throws BusinessException {
        User user = findUserByUuid(uuid);
        return UserResponse.UserInfo.build(user);
    }

    // TODO Exception?
    public List<UserResponse.ProjectInfo> getProjectList(String uuid) {
        User user = findUserByUuid(uuid);
        List<UserResponse.ProjectInfo> projectList = userRepository.getProjectList(user.getId());

        Apidocs apidocs = null;
        for (UserResponse.ProjectInfo project : projectList) {
            apidocs = apidocsRepository.getDocs(project.getProjectId());
            project.setTitle(apidocs.getTitle());
            project.setDesc(apidocs.getDesc());
        }
        return projectList;
    }


}
