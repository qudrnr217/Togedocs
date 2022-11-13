package com.togedocs.backend.api.service;

import com.togedocs.backend.api.dto.UserRequest;
import com.togedocs.backend.api.dto.UserResponse;
import com.togedocs.backend.domain.entity.User;
import com.togedocs.backend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public UserResponse.Id modifyUser(UserRequest.ModifyUserRequest userRequest, String providerId) {
        System.out.println("providerId; " + providerId);
        User userEntity = userRepository.findByProviderId(providerId);

        long num = userRepository.updateByUserInfo(userEntity, userRequest);

        return UserResponse.Id.builder().id((int) num).build();
    }
}
