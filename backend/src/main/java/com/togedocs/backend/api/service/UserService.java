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

    public User findUserByProviderId(String providerId) {
        return userRepository.findByProviderId(providerId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    public void updateUserInfo(UserRequest.ModifyUserRequest userRequest, String providerId) {
        User user = findUserByProviderId(providerId);
        boolean result = userRepository.updateUserInfo(user, userRequest);
        if (!result) {
            throw new BusinessException(ErrorCode.USER_INFO_UPDATE_BAD_REQUEST);
        }
    }

    public UserResponse.UserInfo getUserInfo(String providerId) throws BusinessException {
        User user = findUserByProviderId(providerId);
        return UserResponse.UserInfo.build(user);
    }

    public List<UserResponse.ProjectInfo> getProjectList(String providerId) {
        User user = findUserByProviderId(providerId);
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
