package com.mozylee.book.springboot.config.auth;

import com.mozylee.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // h2-console 화면 사용을 위해 옵션 disable
                .csrf().disable()
                .headers().frameOptions().disable()
                
                .and()
                    // URL 별 권한 관리 설정
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    // 나머지 URL은 모두 인증된 사용자(로그인 완료)가 접근 가능
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            // OAuth2 로그인 성공 이후 후속 조치를 진행할 UserService 구현체
                            // 리소스 서버(소셜 서비스)에서 사용자 정보를 가져온 다음의 기능을 명시
                            .userService(customOAuth2UserService);
    }
}
