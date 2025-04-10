package com.semi.lynk.config;

import com.semi.lynk.function.login.service.LoginLogService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private LoginLogService loginLogService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("Authentication Object: " + authentication);  // 로그 추가
        String empNo = authentication.getName();       // 로그인 사용자 정보
        System.out.println("로그인 성공~ : " + empNo);
        loginLogService.logLogin(empNo);               // 로그인 기록
        response.sendRedirect("/");                 // 성공 후 이동
    }

}
