package com.togedocs.backend.api.service;

import com.togedocs.backend.api.dto.UserRequest;
import com.togedocs.backend.api.dto.UserResponse;
import com.togedocs.backend.common.exception.BusinessException;
import com.togedocs.backend.common.exception.ErrorCode;
import com.togedocs.backend.domain.entity.Apidocs;
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
    private final ProjectUserRepository projectUserRepository;
    //    private final MongoTemplate mongoTemplate;
    private final ApidocsRepository apidocsRepository;

    public User findUserByProviderId(String providerId) {
        return userRepository.findByProviderId(providerId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    public void updateUserInfo(UserRequest.ModifyUserRequest userRequest, String providerId) {
        User user = findUserByProviderId(providerId);
        boolean result = userRepository.updateUserInfo(user, userRequest);
        if (!result) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
    }

    public UserResponse.UserInfo getUserInfo(String providerId) throws BusinessException {
        User user = findUserByProviderId(providerId);
        return UserResponse.UserInfo.build(user);
    }

    public List<UserResponse.ProjectInfo> getProjectInfoList(String providerId) {
        User user = findUserByProviderId(providerId);
//        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        //user가 참여하고있는 프로젝트 id
        List<Long> projectIds = userRepository.getProjectId(user.getId());

        List<UserResponse.ProjectInfo> projectInfoList = new ArrayList<>();
        for (Long projectId : projectIds) {
            //프로젝트 권한
            String role = projectUserRepository.getMyRole(user.getId(), projectId);
            //이름, imgNo
//            List<String> names = userRepository.getNames(projectId, user.getId());
            List<String> names = projectUserRepository.getMemberNames(projectId);
            int imgNo = userRepository.getImgNo(projectId);

            Apidocs apidocs = apidocsRepository.getDocs(projectId);

            //Info 정보 넣기
            // project_user : role -> select pu.role, p.imgNo from pu join project on pu.projectId = p.id where pu.user_id = {userId} and p.id = {projectId}
            // user : names -> select user.name from user join project_user on pu.userId = user.id where pu.projectId = {projectId}
            // project : imgNo -> select project.imgNo from project where project.id = {projectId}
            projectInfoList.add(UserResponse.ProjectInfo.build(projectId, role, apidocs.getTitle(), apidocs.getDesc(), names, imgNo));
//            return UserResponse.Info.build(projectId,names,imgNos);
        }

//        return UserResponse.Info.build(userInfo,projectInfo);
        return projectInfoList;

    }


}
