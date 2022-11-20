package com.togedocs.backend.api.service;

import com.togedocs.backend.api.dto.UserRequest;
import com.togedocs.backend.api.dto.UserResponse;
import com.togedocs.backend.api.exception.IdNotFoundException;
import com.togedocs.backend.domain.entity.Apidocs;
import com.togedocs.backend.domain.entity.User;
import com.togedocs.backend.domain.repository.ProjectUserRepository;
import com.togedocs.backend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final ProjectUserRepository projectUserRepository;
    private final MongoTemplate mongoTemplate;

    public UserResponse.Id modifyUser(UserRequest.ModifyUserRequest userRequest, String providerId) {
        User userEntity = userRepository.findByProviderId(providerId);

        long num = userRepository.updateByUserInfo(userEntity, userRequest);

        return UserResponse.Id.builder().id((int) num).build();
    }

    public UserResponse.userNameAndImgNo getUserNameAndImgNo(Long userId) throws IdNotFoundException {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null)
            throw new IdNotFoundException("userId");
        return UserResponse.userNameAndImgNo.build(user.getName(), user.getImgNo());
    }

    public List<UserResponse.Info> getUserInfo(String providerId) {
        User userEntity = userRepository.findByProviderId(providerId);
//        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        //user가 참여하고있는 프로젝트 id
        List<Long> projectIds = userRepository.getProjectId(userEntity.getId());

        List<UserResponse.Info> list = new ArrayList<>();
        for (Long projectId : projectIds) {
            //자신의 이름
            String myname = userEntity.getName();
            //프로젝트 권한
            String role = projectUserRepository.getMyRole(userEntity.getId(), projectId);
            //이름, imgNo
            List<String> names = userRepository.getNames(projectId, userEntity.getId());
            int imgNos = userRepository.getImgNo(projectId);
            //title
            Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
            Apidocs apidocs = mongoTemplate.findOne(query, Apidocs.class, "apidocs");
            String title = apidocs.getTitle();
            //desc
            String desc = apidocs.getDesc();
            //Info 정보 넣기
            list.add(UserResponse.Info.build(myname, role, projectId, title, desc, names, imgNos));
//            return UserResponse.Info.build(projectId,names,imgNos);
        }

//        return UserResponse.Info.build(userInfo,projectInfo);
        return list;

    }


}
