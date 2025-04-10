package com.semi.lynk.config;

import com.semi.lynk.common.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthFailHandler authFailHandler;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 정적 리소스에 대한 요청은 시큐리티 설정에 들지 못하게 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return roleHierarchy;
    }

//    @Bean
//    public DefaultWebSecurityExpressionHandler customExpressionHandler(RoleHierarchyImpl roleHierarchy) {
//        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
//        expressionHandler.setRoleHierarchy(roleHierarchy);
//        return expressionHandler;
//    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/login", "/login/empAdd").permitAll();    // 인증되지 않은 사용자들이 접근하는 URL
            auth.requestMatchers("/management/**").hasAnyAuthority("ROLE_ADMIN");
            auth.requestMatchers("/**").hasAnyAuthority("ROLE_USER");
            auth.anyRequest().authenticated();     // 로그인 된 인원에 한해 전부 들어갈 수 있음
        }).formLogin(login -> {
            login.loginPage("/login")
                 .successHandler(loginSuccessHandler);
            login.usernameParameter("empId");       // 사용자가 ID를 입력하는 필드(input 타입 name 과 반드시 일치)
            login.passwordParameter("empPwd");      // 사용자가 PWD 를 입력하는 필드(input 타입 name 과 반드시 일치)
            login.failureHandler(authFailHandler);  // 로그인 실패시 내용 기술 객체 호출(해야함)
        }).logout(logout -> {       // 로그아웃을 담당할 핸들러 메소드 요청 URL 기술
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                  .logoutSuccessHandler(logoutSuccessHandler);
            logout.deleteCookies("JSESSIONID");     // session 쿠키 삭제로 로그아웃
            logout.invalidateHttpSession(true);     // 서버 세션 공간 만료
            logout.logoutSuccessUrl("/login");      // 로그아웃 성공 시 요청 URL
        }).sessionManagement(session -> {
            session.maximumSessions(1);             // session 의 허용 갯수 제한 (여러개 활성화 방지
            session.invalidSessionUrl("/login");    // 세션이 만료 되었을 때 요청할 URL 기술
        })
        .csrf(csrf -> csrf.disable());
        // 위는 csrf 설정 비활성화. 아래처럼 활성화하는 것이 보안적으로 우수.
        // .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));


        return http.build();

        // 로그인 작업이 완료되기 전 다른 작업을 위해 모든 페이지 접근 권한 허용 //
//        http.authorizeHttpRequests(auth -> {
//            auth.anyRequest().permitAll();        // 모든 요청 허용
//        }).csrf(csrf -> csrf.disable());          // CSRF 보호 비활성화
//        return http.build();
    }

}
