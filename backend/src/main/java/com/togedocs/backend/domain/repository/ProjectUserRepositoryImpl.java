package com.togedocs.backend.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.togedocs.backend.api.dto.ProjectRequest;
import com.togedocs.backend.api.dto.QUserDto;
import com.togedocs.backend.api.dto.UserDto;
import com.togedocs.backend.domain.entity.QProjectUser;
import com.togedocs.backend.domain.entity.QUser;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
 public class ProjectUserRepositoryImpl implements ProjectUserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<UserDto> getMembers(Long projectId) {
        QUser user = QUser.user;
        QProjectUser projectUser = QProjectUser.projectUser;

        List<UserDto> members = jpaQueryFactory.select(new QUserDto(user.id, user.name, user.imgNo, projectUser.role))
                .from(user)
                .rightJoin(projectUser)
                .on(user.id.eq(projectUser.user.id))
                .where(projectUser.project.id.eq(projectId))
                .fetch();
        return members;
    }

    @Override
    public Long updateMemberRole(Long projectId, ProjectRequest.UpdateMemberRoleRequest request) {
        QUser user = QUser.user;
        QProjectUser projectUser = QProjectUser.projectUser;

        Long updatedResult = jpaQueryFactory.update(projectUser)
                .where((projectUser.project.id.eq(projectId)).and(projectUser.user.id.eq(request.getUserId())))
                .set(projectUser.role, request.getRole())
                .execute();
        return updatedResult;
    }

    @Override
    public String getMyRole(Long user_id,Long project_id) {
        QUser user = QUser.user;
        QProjectUser projectUser = QProjectUser.projectUser;

        String myname = String.valueOf(jpaQueryFactory.select(projectUser.role)
                .from(user)
                .join(projectUser)
                .on(user.id.eq(projectUser.user.id))
                .where(projectUser.user.id.eq(user_id).and(projectUser.project.id.eq(project_id)))
                .fetchOne());

        return myname;
    }

    @Override
    public List<String> getMemberNames(Long projectId) {
        QUser user = QUser.user;
        QProjectUser projectUser = QProjectUser.projectUser;
        List<String> memberNames = jpaQueryFactory.select(user.name)
                .from(user)
                .join(projectUser)
                .on(user.id.eq(projectUser.user.id))
                .where(projectUser.project.id.eq(projectId))
                .fetch();
        return memberNames;
    }
}
