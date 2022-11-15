package com.togedocs.backend.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import com.togedocs.backend.api.dto.UserRequest;
import com.togedocs.backend.domain.entity.QProject;
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


    @Override
    public List<Long> getProjectId(Long user_id) {
        QProjectUser projectUser = QProjectUser.projectUser;
        QUser user = QUser.user;

        List<Long> projectIds = jpaQueryFactory.select(projectUser.project.id)
                .from(projectUser)
                .join(user)
                .on(user.id.eq(projectUser.user.id))
                .where(projectUser.user.id.eq(user_id))
                .fetch();
        return projectIds;
    }

    @Override
    public List<String> getNames(Long projectId, Long id) {
        QProjectUser projectUser = QProjectUser.projectUser;
        QUser user = QUser.user;

        List<String> names = jpaQueryFactory.select(user.name)
                .from(projectUser)
                .join(user)
                .on(projectUser.user.id.eq(user.id))
                .where(projectUser.project.id.eq(projectId))
                .fetch();

        return names;
    }

    @Override
    public int getImgNo(Long projectId) {
        QProject project =QProject.project;

        int imgNo = jpaQueryFactory.select(project.imgNo)
                .from(project)
                .where(project.id.eq(projectId))
                .fetchOne();

        return imgNo;
    }


    //TODO title

}
