package com.togedocs.backend.domain.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.togedocs.backend.api.dto.UserRequest;
import com.togedocs.backend.api.dto.UserResponse;
import com.togedocs.backend.domain.entity.QProject;
import com.togedocs.backend.domain.entity.QProjectUser;
import com.togedocs.backend.domain.entity.QUser;
import com.togedocs.backend.domain.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean updateUserInfo(Long userId, UserRequest.ModifyUserRequest userRequest) {
        QUser user = QUser.user;
        long num = jpaQueryFactory.update(user)
                .where(user.id.eq(userId))
                .set(user.name, userRequest.getName())
                .set(user.imgNo, userRequest.getImgNo())
                .execute();
        return num > 0;
    }

    @Override
    public List<UserResponse.ProjectInfo> getProjectList(Long userId) {
        QUser user = QUser.user;
        QProject project = QProject.project;
        QProjectUser projectUser = QProjectUser.projectUser;

        List<UserResponse.ProjectInfo> projectList = new ArrayList<>();

        List<Tuple> myProjects = jpaQueryFactory.select(project.id, project.imgNo, projectUser.role)
                .from(project)
                .join(projectUser)
                .on(project.id.eq(projectUser.project.id))
                .where(projectUser.user.id.eq(userId))
                .fetch();


        for (Tuple myProject : myProjects) {
            List<String> names = jpaQueryFactory.select(user.name)
                    .from(user)
                    .join(projectUser)
                    .on(user.id.eq(projectUser.user.id))
                    .where(projectUser.project.id.eq(myProject.get(project.id)))
                    .fetch();
            projectList.add(UserResponse.ProjectInfo.build(myProject.get(project.id), myProject.get(project.imgNo), myProject.get(projectUser.role), names));
        }
        return projectList;
    }

}
