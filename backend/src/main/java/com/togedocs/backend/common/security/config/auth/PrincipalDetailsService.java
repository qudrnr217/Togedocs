package com.togedocs.backend.common.security.config.auth;


import com.togedocs.backend.domain.entity.User;
import com.togedocs.backend.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//시큐리티 설정에서 loginProcessingUrl("/login")
// /login요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    //시큐리티 세션(내부 Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String providerId) throws UsernameNotFoundException {

        User userEntitiy =userRepository.findByProviderId(providerId);
        if(userEntitiy != null){
            //유저가 있을 경우
            return new PrincipalDetails(userEntitiy);
            //리턴이 되면 Authentication(내부에 UserDetails 객체가 들어감)
            //그러면 세션내부에는 Authentication객체가 들어가게 됨.
        }
        return null;
    }
}
