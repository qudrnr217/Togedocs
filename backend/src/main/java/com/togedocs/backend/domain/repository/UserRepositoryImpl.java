package com.togedocs.backend.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import com.togedocs.backend.api.dto.UserRequest;
import com.togedocs.backend.domain.entity.QUser;
import com.togedocs.backend.domain.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public long updateByUserInfo(User userEntity, UserRequest.ModifyUserRequest userRequest) {

        QUser user = QUser.user;


        return jpaQueryFactory.update(user)
                .where(user.id.eq(userEntity.getId()))
                .set(user.name,userRequest.getName())
                .set(user.imgNo,userRequest.getImgNo())
//                .set(user.email,userEntity.getEmail())
//                .set(user.provider,userEntity.getProvider())
//                .set(user.providerId,userEntity.getProviderId())
                .execute();
    }
}
