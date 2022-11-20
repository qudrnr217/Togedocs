package com.togedocs.backend.common.security.config;

import com.togedocs.backend.common.security.config.jwt.JwtAuthFilter;
import com.togedocs.backend.common.security.config.jwt.OAuth2SuccessHandler;
import com.togedocs.backend.common.security.config.jwt.TokenService;
import com.togedocs.backend.common.security.config.oauth.PrincipalOauth2UserService;
import com.togedocs.backend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity //스프링시큐리티 필터가 스프링 필터체인에 등록이 된다.
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final TokenService tokenService;

//    private final CorsFilter corsFilter;
    private final UserRepository userRepository;

    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.csrf().disable();

//        http.authorizeRequests()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션을 사용하지 않겠다는 뜻임.
                .and()
//                .addFilter(corsFilter)
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/api/user/**").authenticated()
                .antMatchers("/api/project/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(new JwtAuthFilter(tokenService, userRepository),UsernamePasswordAuthenticationFilter.class)
                .oauth2Login()
                .defaultSuccessUrl("myapp://")
                .userInfoEndpoint().userService(principalOauth2UserService)
                .and()
                .successHandler(oAuth2SuccessHandler)
                .and()
                .logout()
                .logoutUrl("/api/logout");






        return http.build();
    }




    /*
    기존: WebSecurityConfigurerAdapter를 상속하고 configure매소드를 오버라이딩하여 설정하는 방법
    => 현재: SecurityFilterChain을 리턴하는 메소드를 빈에 등록하는 방식(컴포넌트 방식으로 컨테이너가 관리)
    //https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter

    @Override
    protected void configure(HttpSecurity http) throws  Exception{
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin").access("\"hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll();
    }

     */
}
