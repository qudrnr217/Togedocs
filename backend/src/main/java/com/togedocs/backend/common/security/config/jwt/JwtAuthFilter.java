package com.togedocs.backend.common.security.config.jwt;


import com.togedocs.backend.domain.entity.User;
import com.togedocs.backend.common.security.config.auth.PrincipalDetails;
import com.togedocs.backend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {

    private final TokenService tokenService;
    private final UserRepository userRepository;



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = ((HttpServletRequest)request).getHeader("Authorization");


        //JWT 토큰을 검증을해서 정상적인 사용자인지 확인
        if(token == null || !token.startsWith("Bearer")){
            chain.doFilter(request,response);
            return;
        }

        token = token.replace("Bearer ","");
        if (token != null && tokenService.verifyToken(token)) {
            String email = tokenService.getUid(token);
            User userEntity = userRepository.findByEmail(email);

            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

            Authentication auth = new UsernamePasswordAuthenticationToken(principalDetails,null,null);

            SecurityContextHolder.getContext().setAuthentication(auth);
            chain.doFilter(request,response);

        } else {
            ((HttpServletResponse)response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        //access Token이 expired 됐을 경우
//        if(!tokenService.verifyToken(token)){
//
//        }

    }
}
