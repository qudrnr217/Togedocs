package com.togedocs.backend.common.security.config.jwt;


import com.togedocs.backend.api.dto.User;
import com.togedocs.backend.common.security.config.auth.PrincipalDetails;
import com.togedocs.backend.common.security.repository.UserRepository;
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
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {

    private final TokenService tokenService;
    public UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("인증이나 권한이 필요한 주소 요청이 됨.");
        String token = ((HttpServletRequest)request).getHeader("Authorization");
        System.out.println(token);


        //JWT 토큰을 검증을해서 정상적인 사용자인지 확인
        if(token == null || !token.startsWith("Bearer")){
            chain.doFilter(request,response);
            return;
        }

        token = token.replace("Bearer ","");
        if (token != null && tokenService.verifyToken(token)) {
            String email = tokenService.getUid(token);
            User userEntity = userRepository.findByEmail(email);
            System.out.println("userEntity: "+userEntity);

            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

            Authentication auth = new UsernamePasswordAuthenticationToken(principalDetails,null,null);

            SecurityContextHolder.getContext().setAuthentication(auth);
            chain.doFilter(request,response);

        }

    }
}
