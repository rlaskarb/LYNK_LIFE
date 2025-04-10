package com.semi.lynk.function.db_management.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeDTO {
    // 사번
    private String employeeNo; // 사번
    private String password; // 패스워드
    private String employeeName; // 이름
    private String email; // 이메일
    private int memberStatus; // 계정상태
    private int loginFailCount; // 로그인 실패횟수
    private int workingStatus; // 출근상태
    private int departmentNo; // 부서번호
    private String image; // 사진

}

