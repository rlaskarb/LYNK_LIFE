package com.semi.lynk.function.login.controller;

import com.semi.lynk.function.login.model.dto.EmpAddDTO;
import com.semi.lynk.function.login.model.dto.LoginDTO;
import com.semi.lynk.function.login.service.LoginLogService;
import com.semi.lynk.function.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("login/*")
public class LoginController {

    //****************************************************************
    // 회원가입 페이지 먼저 구성...
    //****************************************************************
    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginLogService loginLogService;

    @GetMapping("empAdd")
    public String empAddPage() {
//        System.out.println("getmapping은 되니?");
        return "function/login/empAdd";
    }

    @PostMapping("empAdd")
    public ModelAndView empAddPage(@ModelAttribute EmpAddDTO empAddDTO,
                                   @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
                                   ModelAndView mv) {

        String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/profile/";
        String profileImagePath = null;

        try {
            if (profileImage != null && !profileImage.isEmpty()) {
                String fileName = UUID.randomUUID() + "_" + profileImage.getOriginalFilename();
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();         // 디렉토리 없으면 생성~
                }
                profileImage.transferTo(new File(UPLOAD_DIR + fileName));
                profileImagePath = "/profile/" + fileName;
            }

            // DTO 에 이미지 경로 설정
            empAddDTO.setImage(profileImagePath);

            Integer result = loginService.addEmployee(empAddDTO);
            String message = null;

            if (result == null) {
                message = "중복 된 회원이 존재합니다.";
            } else if (result == 0) {
                message = "서버 내부에서 오류가 발생했습니다.";
                mv.setViewName("function/login/empAdd");
            } else if (result >= 1) {
                message = "회원 가입이 완료되었습니다.";
                mv.setViewName("function/management/activeAccountList");
            }

            mv.addObject("message", message);
        } catch (Exception e) {
            mv.setViewName("function/login/empAdd");
            mv.addObject("message", "파일 업로드 중 오류가 발생했습니다.");
            e.printStackTrace();
        }

        return mv;

    }

    //****************************************************************
    // 로그인
    //****************************************************************

    @GetMapping("login")
    public void loginPage() {}

    @GetMapping("failLogin")
    public ModelAndView loginFail(@RequestParam String message, ModelAndView mv) {

        // 실패시 핸들러에서 쿼리스트링으로 보내주는 errorMessage
        mv.addObject("message", message);
        mv.setViewName("failLogin");
        return mv;
    }

    //****************************************************************
    // 로그인 시간 기록
    //****************************************************************
    @PostMapping("login")
    public String login(String empNo) {
        loginLogService.logLogin((empNo));
        return "Login logged";
    }

    @PostMapping("logout")
    public String logout(String empNo) {
        loginLogService.logLogin((empNo));
        return "Logout logged";
    }

}
