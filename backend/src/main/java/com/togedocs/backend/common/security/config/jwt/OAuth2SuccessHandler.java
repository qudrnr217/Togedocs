package com.togedocs.backend.common.security.config.jwt;


import com.togedocs.backend.api.dto.Token;
import com.togedocs.backend.domain.entity.User;
import com.togedocs.backend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenService tokenService;

    private final UserRepository userRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");
        String name = (String) oAuth2User.getAttributes().get("name");

        User user_id=userRepository.findByEmail(email);

        log.info("Principal에서 꺼낸 OAuth2User = {}", oAuth2User);
        // 최초 로그인이라면 회원가입 처리를 한다.
        String targetUrl;
        log.info("토큰 발행 시작");

        Token token = tokenService.generateToken(user_id.getId(),user_id.getName(),user_id.getImgNo(), email);
        log.info("{}", token);
//        targetUrl = UriComponentsBuilder.fromUriString("/api/user/getToken")
        targetUrl = UriComponentsBuilder.fromUriString("myapp://")
                .queryParam("token", token)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

        response.addHeader("Authorization","Bearer "+token.getToken());
        //왜 redirect를 controller로 해야하는지 모르겠음.
        //queryParam으로 하면 보안적으로 문제점이 있지않나요 ??

//        super.onAuthenticationSuccess(request, response, authentication);
    }
}
