package com.semi.lynk.function.login.model.dao;

import com.semi.lynk.function.login.model.dto.EmpAddDTO;
import com.semi.lynk.function.login.model.dto.LoginDTO;
import com.semi.lynk.function.login.model.dto.LoginLogDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoginMapper {

    int addEmployee(EmpAddDTO empAddDTO);
    int addAuthorization(EmpAddDTO empAddDTO);

    // 사용자 정보 가져오기
    LoginDTO findByUsername(String empName);

    // 로그인 로깅
    void insertLoginLog(LoginLogDTO loginLogDTO);       // 로그 기록
    LoginLogDTO selectLatestLogByEmpNo(String empNo);   // 최근 로그인 시간
    List<LoginLogDTO> selectLogsByEmpNo(String empNo);  // 계정별 로그인 시간 목록

    // 출근
    int checkWorkOn(String empNo, String workDate);
    void updateWorkingStatusToIn(String empNo);
    void insertWorkOn(LoginLogDTO loginLogDTO);

    // 퇴근
    int checkWorkOff(String empNo, String workDate);
    void updateWorkingStatusToOut(String empNo);
    void updateWorkOff(LoginLogDTO loginLogDTO);

    // 외근 시작
    void updateWorkingStatusToOutsideOn(String empNo);
    void updateWorkOutsideOn(LoginLogDTO loginLogDTO);

    // 외근 종료
    void updateWorkingStatusToOutsideOff(String empNo);
    void updateWorkOutsideOff(LoginLogDTO loginLogDTO);

}
