package com.semi.lynk.function.login.service;

import com.semi.lynk.function.login.model.dao.LoginMapper;
import com.semi.lynk.function.login.model.dto.LoginLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class LoginLogService {

    @Autowired
    private LoginMapper loginMapper;

    //****************************************************************
    // 로그인 기록 내용들
    //****************************************************************
    // 로그인 로깅
    public void logLogin(String empNo) {
        LoginLogDTO loginLogDTO = new LoginLogDTO();
        loginLogDTO.setEmpNo(empNo);
        loginLogDTO.setLoginStatus(1);  // 로그인
        loginLogDTO.setLoginTime(Timestamp.valueOf(LocalDateTime.now()));
        loginMapper.insertLoginLog(loginLogDTO);
    }

    // 로그아웃 로깅
    public void logLogout(String empNo) {
        LoginLogDTO loginLogDTO = new LoginLogDTO();
        loginLogDTO.setEmpNo(empNo);
        loginLogDTO.setLoginStatus(0);  // 로그아웃
        loginLogDTO.setLoginTime(Timestamp.valueOf(LocalDateTime.now()));
        loginMapper.insertLoginLog(loginLogDTO);
    }

    // 최근 로그인 리턴
    public LoginLogDTO getLatestLog(String empNo) {
        return loginMapper.selectLatestLogByEmpNo(empNo);
    }

    public void processWorkOn(LoginLogDTO loginLogDTO) {
        int count = loginMapper.checkWorkOn(loginLogDTO.getEmpNo(), loginLogDTO.getWorkDate());
        if (count > 0) {
            throw new IllegalArgumentException("이미 출근 기록이 있습니다.");
        }
        loginMapper.updateWorkingStatusToIn(loginLogDTO.getEmpNo());
        loginMapper.insertWorkOn(loginLogDTO);
    }

    public void processWorkOff(LoginLogDTO loginLogDTO) {
        int count = loginMapper.checkWorkOff(loginLogDTO.getEmpNo(), loginLogDTO.getWorkDate());
        if (count > 0) {
            throw new IllegalArgumentException("이미 퇴근 기록이 있습니다.");
        }
        loginMapper.updateWorkingStatusToOut(loginLogDTO.getEmpNo());
        loginMapper.updateWorkOff(loginLogDTO);
    }

    public void processWorkOutsideOn(LoginLogDTO loginLogDTO) {
        loginMapper.updateWorkingStatusToOutsideOn(loginLogDTO.getEmpNo());
        loginMapper.updateWorkOutsideOn(loginLogDTO);
    }

    public void processWorkOutsideOff(LoginLogDTO loginLogDTO) {
        loginMapper.updateWorkingStatusToOutsideOff(loginLogDTO.getEmpNo());
        loginMapper.updateWorkOutsideOff(loginLogDTO);
    }
}
