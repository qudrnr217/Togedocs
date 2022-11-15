package com.togedocs.backend.common.security.config.auth;

import com.togedocs.backend.domain.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user; //콤포지션
    private Map<String, Object> attributes;

    //생성자
    //일반 로그인 생성자
    public PrincipalDetails(User user) {
        this.user = user;
    }

    //Oauth로그인 생성자
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }


    //해당 유저의 권한을 리턴하는 곳!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
//                return user.getRole();
                return null;
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getProviderId();
    }

    //계정이 만료 되었는지.
    @Override
    public boolean isAccountNonExpired() {
        return true; //아니오가 true임. 즉 계정이 만료가 안됨.
    }

    //너의 계정이 잠겼니?
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //너의 계정의 비밀번호가 1년이 지났니? 즉 비밀번호가 너무 오래사용한거 아니니?
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //너의 계정이 활성화 되어있니?
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
