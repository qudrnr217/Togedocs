package com.togedocs.backend.common.security.config.oauth;


import com.togedocs.backend.domain.entity.User;
import com.togedocs.backend.common.security.config.auth.PrincipalDetails;
import com.togedocs.backend.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder =new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    //구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
    @Override//여기서 후처리가 되어야함.
    //코드는 안받고 바로 accessToken과 사용자의 정보가 userRequest에 바로 리턴이 된다.
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        //우리가 구글로그인버튼을 클릭-> 구글로그인창->로그인을 완료->code를 리턴(Oauth-Client라이브러리가 받음)->Access토큰 요청
        //여기까지가 userRequest정보->loadUser함수 호출->구글로부터 회원프로필 받아준다.


        //회원가입을 강제로 진행해볼 예정.
        String provider=userRequest.getClientRegistration().getRegistrationId(); //google
        String sub = oauth2User.getAttribute("sub");
        String providerId = provider+"_"+sub; //google_103823801447068230340
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        int imgNo = 1;



        User userEntity = userRepository.findByProviderId(providerId);
        if(userEntity == null){
            userEntity = User.builder()
                    .imgNo(imgNo)
                    .providerId(providerId)
                    .email(email)
                    .provider(provider)
                    .name(name)
                    .build();
            userRepository.save(userEntity);

        }

        //PrincipalOauth2UserService를 만든이유는
        //1.principalDetails로 return 하기위해서
        //2.Oauth로 로그인했을 때 회원가입을 강제로 진행시키기 위해서 한것임.

        //Map<String,Object>
        //Oauth2User 타입은 PrincipalDetails에 상속받았기 때문에 return으로 PrincipalDetails를 해도된다.
        return new PrincipalDetails(userEntity,oauth2User.getAttributes());
    }
}