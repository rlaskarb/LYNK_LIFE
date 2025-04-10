package com.semi.lynk.common;

import com.semi.lynk.function.login.model.EmpDetails;
import com.semi.lynk.function.login.model.dto.LoginDTO;
import com.semi.lynk.function.login.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    @Autowired
    private LoginService loginService;

//    @GetMapping("/")
//    public String list (){
//        return "common/main";
//    }

    @GetMapping("/")
    public ModelAndView mainPage(@AuthenticationPrincipal EmpDetails empDetails, HttpSession session, ModelAndView mv) {

        // 세션에 사용자 정보 저장
        session.setAttribute("empNo", empDetails.getUsername());
        session.setAttribute("empName", empDetails.getName());
        session.setAttribute("empDetails", empDetails);
        session.setAttribute("deptName", empDetails.getLoginDTO().getDepName());
        session.setAttribute("position", empDetails.getLoginDTO().getPosition());
        session.setAttribute("image", empDetails.getLoginDTO().getImage());

        // 권한 관련 데이터
        session.setAttribute("roleAdmin", empDetails.getLoginDTO().getRoleAdmin());             // 관리자 권한, management 페이지 접근 가능자, 0:권한 없음 / 1:권한 있음
        System.out.println("roleAdmin: " + empDetails.getLoginDTO().getRoleAdmin());
        session.setAttribute("roleDraft", empDetails.getLoginDTO().getRoleDraft());             // 기안 승인 권한, 0:권한 없음 / 1:권한 있음
        session.setAttribute("roleLeave", empDetails.getLoginDTO().getRoleLeave());             // 연차 승인 권한, 0:권한 없음 / 1:권한 있음
        session.setAttribute("roleDepartment", empDetails.getLoginDTO().getRoleDepartment());   // 부서 관리 권한, 0:권한 없음 / 1:권한 있음
        session.setAttribute("roleNotice", empDetails.getLoginDTO().getRoleNotice());           // 게시글 작성 권한, 0:권한 없음 / 1:권한 있음
        session.setAttribute("roleSchedule", empDetails.getLoginDTO().getRoleSchedule());       // 일정 관리 권한, 0:개인 / 1:부서 / 2:전사

        // 권한 관련 데이터
        int roleAdmin = empDetails.getLoginDTO().getRoleAdmin(); // 관리자 권한
        int roleDraft = empDetails.getLoginDTO().getRoleDraft(); // 기안 승인 권한
        int roleLeave = empDetails.getLoginDTO().getRoleLeave(); // 연차 승인 권한
        int roleDepartment = empDetails.getLoginDTO().getRoleDepartment(); // 부서 관리 권한
        int roleNotice = empDetails.getLoginDTO().getRoleNotice(); // 게시글 작성 권한
        int roleSchedule = empDetails.getLoginDTO().getRoleSchedule(); // 일정 관리 권한

        // 모델에 추가
        mv.addObject("user", empDetails);
        mv.addObject("roleAdmin", roleAdmin);
        mv.addObject("roleDraft", roleDraft);
        mv.addObject("roleLeave", roleLeave);
        mv.addObject("roleDepartment", roleDepartment);
        mv.addObject("roleNotice", roleNotice);
        mv.addObject("roleSchedule", roleSchedule);

        // 모델에 추가
        mv.addObject("user", empDetails);
        System.out.println("session = " + session.getAttribute("empName"));
        mv.setViewName("common/main");
        return mv;
    }

    @GetMapping("login")
    public String loginPage(){
        return "function/login/login";
    }

    @GetMapping("passwordRequest")
    public String passwordRequestPage(){
        return "function/login/passwordRequest";
    }

    @GetMapping("passwordReset")
    public String passwordResetPage(){
        return "function/login/passwordReset";
    }

}
