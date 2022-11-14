package com.togedocs.backend.api.service;

import com.querydsl.core.Tuple;
import com.togedocs.backend.api.dto.UserRequest;
import com.togedocs.backend.api.dto.UserResponse;
import com.togedocs.backend.domain.entity.User;
import com.togedocs.backend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

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

    public UserResponse.Info getUserInfo(String providerId){
        User userEntity = userRepository.findByProviderId(providerId);

//        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));


        List<User> userInfo = userRepository.getUserInfo(userEntity.getId());
        List<Long> projectInfo = userRepository.getProjectInfo(userEntity.getId());



        for(User a : userInfo){
            a.getId()
        }

        return UserResponse.Info.build(userInfo,projectInfo);

    }
}
