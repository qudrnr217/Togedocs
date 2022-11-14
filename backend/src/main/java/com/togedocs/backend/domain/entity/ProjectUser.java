package com.togedocs.backend.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ProjectUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Project.class)
    @JoinColumn(name = "project_id", nullable = false)
    Project project;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Enumerated(EnumType.STRING)
    private ProjectUserRole role;

    @Builder
    public ProjectUser(Project project, User user, ProjectUserRole role){
        this.project = project;
        this.user = user;
        this.role = role;
    }
}
