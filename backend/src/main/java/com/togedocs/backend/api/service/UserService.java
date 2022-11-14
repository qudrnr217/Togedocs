package com.togedocs.backend.api.service;

import com.togedocs.backend.api.dto.UserRequest;
import com.togedocs.backend.api.dto.UserResponse;
import com.togedocs.backend.domain.entity.User;
import com.togedocs.backend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private MongoTemplate mongoTemplate;

    public UserResponse.Id modifyUser(UserRequest.ModifyUserRequest userRequest, String providerId) {
        System.out.println("providerId; " + providerId);
        User userEntity = userRepository.findByProviderId(providerId);

        long num = userRepository.updateByUserInfo(userEntity, userRequest);

        return UserResponse.Id.builder().id((int) num).build();
    }

    public List<UserResponse.Info> getUserInfo(String providerId){
        User userEntity = userRepository.findByProviderId(providerId);

//        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));

        //user가 참여하고있는 프로젝트 id
        List<Long> projectIds = userRepository.getProjectId(userEntity.getId());
        List<UserResponse.Info> list = new ArrayList<>();
        for(Long projectId : projectIds){
            System.out.println("ProjectId: "+projectId);
            //이름, imgNo
            List<String> names = userRepository.getNames(projectId,userEntity.getId());
            List<Integer> imgNos = userRepository.getImgNo(projectId);
            //title

            //desc

            //Info 정보 넣기
            list.add(UserResponse.Info.build(projectId,names,imgNos));
//            return UserResponse.Info.build(projectId,names,imgNos);
        }

//        return UserResponse.Info.build(userInfo,projectInfo);
        return list;

    }
}
