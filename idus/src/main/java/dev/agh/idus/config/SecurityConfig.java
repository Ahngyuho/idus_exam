package dev.agh.idus.config;

import dev.agh.idus.config.filter.JwtFilter;
import dev.agh.idus.config.filter.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationConfiguration configuration;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Component vs @Bean
    // @Component : 프로젝트가 실행될 때 컴포넌트 스캔을 통해서 객체를 생성해서 스프링 컨테이너에 Bean으로 등록
    //              개발자가 직접 개발한 클래스의 객체를 등록할 때 사용
    // @Bean : 컴포넌트 스캔 X, 개발자가 직접 객체를 생성해서 스프링 컨테이너에 Bean으로 등록
    //              라이브러리를 가져와서 라이브러리의 객체를 등록할 때 사용
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // ❌ CSRF, HTTP Basic, Form Login, 세션 관리 비활성화 (무상태 API 방식)
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable);

        // ✅ 인증 예외 처리 (로그인, 회원가입, 로그아웃, Swagger UI 허용)
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/login", "/member/signup", "/logout",
                        "/swagger-ui/**", "/v3/api-docs/**",
                        "/swagger-resources/**", "/webjars/**"
                ).permitAll()
                //.requestMatchers("/course/create").hasRole("INSTRUCTOR") // 특정 권한 필요할 경우 추가
                .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
        );

        // ✅ 로그아웃 설정 (ATOKEN 쿠키 삭제)
        http.logout(logout -> logout
                .deleteCookies("ATOKEN")
                .permitAll()
        );

        // ✅ 커스텀 필터 추가 (로그인 & JWT 처리)
        http.addFilterAt(new LoginFilter(configuration.getAuthenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
