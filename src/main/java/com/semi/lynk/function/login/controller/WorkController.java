package com.semi.lynk.function.login.controller;

import com.semi.lynk.function.login.model.dto.LoginLogDTO;
import com.semi.lynk.function.login.service.LoginLogService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WorkController {

    @Autowired
    private LoginLogService loginLogService;

    @PostMapping("/work-on")
    @ResponseBody
    public ResponseEntity<String> handleWorkOn(@RequestBody LoginLogDTO loginLogDTO, HttpSession session) {
        String empNo = (String) session.getAttribute("empNo");
        if (empNo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        loginLogDTO.setEmpNo(empNo);

        try {
            loginLogService.processWorkOn(loginLogDTO);
            return ResponseEntity.ok("출근 처리 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("출근 처리 실패");
        }
    }

    @PostMapping("/work-off")
    @ResponseBody
    public ResponseEntity<String> handleWorkOff(@RequestBody LoginLogDTO loginLogDTO, HttpSession session) {
        String empNo = (String) session.getAttribute("empNo");
        if (empNo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        loginLogDTO.setEmpNo(empNo);

        try {
            loginLogService.processWorkOff(loginLogDTO);
            return ResponseEntity.ok("퇴근 처리 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("퇴근 처리 실패");
        }
    }

    @PostMapping("/work-outside-on")
    @ResponseBody
    public ResponseEntity<String> handleWorkOutsideOn(@RequestBody LoginLogDTO loginLogDTO, HttpSession session) {
        System.out.println("외근 도착");
        String empNo = (String) session.getAttribute("empNo");
        if (empNo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        loginLogDTO.setEmpNo(empNo);

        try {
            System.out.println("외근 시작");
            loginLogService.processWorkOutsideOn(loginLogDTO);
            return ResponseEntity.ok("외근 시작 처리 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("외근 시작 처리 실패");
        }
    }

    @PostMapping("/work-outside-off")
    @ResponseBody
    public ResponseEntity<String> handleWorkOutsideOff(@RequestBody LoginLogDTO loginLogDTO, HttpSession session) {
        String empNo = (String) session.getAttribute("empNo");
        if (empNo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        loginLogDTO.setEmpNo(empNo);

        try {
            loginLogService.processWorkOutsideOff(loginLogDTO);
            return ResponseEntity.ok("외근 종료 처리 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("외근 종료 처리 실패");
        }
    }

}
