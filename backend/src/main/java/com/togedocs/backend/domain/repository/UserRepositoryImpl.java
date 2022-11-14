package com.togedocs.backend.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import com.togedocs.backend.api.dto.UserRequest;
import com.togedocs.backend.domain.entity.QProjectUser;
import com.togedocs.backend.domain.entity.QUser;
import com.togedocs.backend.domain.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

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

    //User가 참여하고있는 project
    @Override
    public List<User> getUserInfo(Long id) {
        QUser user = QUser.user;
        QProjectUser projectUser = QProjectUser.projectUser;

        List<User> users = jpaQueryFactory.select(user)
                .from(user)
                .join(projectUser)
                .on(user.id.eq(projectUser.project.id))
                //join -> project 로 들어가
                .where(projectUser.user.id.eq(id))
                .fetch();



        return users;
    }

    @Override
    public List<Long> getProjectInfo(Long id) {
        QUser user = QUser.user;
        QProjectUser projectUser = QProjectUser.projectUser;

        List<Long> projects = jpaQueryFactory.select(projectUser.project.id)
                .from(user)
                .join(projectUser)
                .on(user.id.eq(projectUser.project.id))
                .where(projectUser.user.id.eq(id))
                .fetch();

        return projects;
    }


    //TODO title

}
