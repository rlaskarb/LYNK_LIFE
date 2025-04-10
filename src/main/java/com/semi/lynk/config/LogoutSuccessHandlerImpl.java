package com.semi.lynk.config;

import com.semi.lynk.function.login.service.LoginLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private LoginLogService loginLogService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication != null) {
            String empNo = authentication.getName();    // 로그아웃 사용자
            loginLogService.logLogout(empNo);              // 로그아웃 로그 기록
        }
        response.sendRedirect("/login");             // 로그아웃 성공 후 로그인 페이지로 리다이렉트
    }
}
